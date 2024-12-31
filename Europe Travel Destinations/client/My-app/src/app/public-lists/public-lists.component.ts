import { Component, OnInit } from '@angular/core';
import { PublicListService } from './public-lists.service';
import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef } from '@angular/core';

interface Destination {
  Destination: string;
  Country: string;
}

interface SavedList {
  title: string;
  destinations: Destination[];
}

@Component({
  selector: 'app-public-lists',
  templateUrl: './public-lists.component.html',
  styleUrls: ['./public-lists.component.css'],
})
export class PublicListsComponent implements OnInit {
  publicLists: any[] = [];
  savedLists: any[] = [];
  newListName: string = '';
  newListItems: string = '';
  isLoggedIn: boolean = false;
  showSavedListsDropdown: boolean = false;
  selectedSavedList: string = '';
  selectedListForReview: any = null;
  reviewRating: number = 0;
  reviewComment: string = '';
  selectedListForModal: any = null;
  showReviewsModal: boolean = false;
  isAdmin: boolean = false;
  expandedList: string | null = null;

  constructor(private publicListService: PublicListService, private http: HttpClient, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.fetchPublicLists();
    this.checkLoginStatus();
    this.fetchSavedLists();
  }

  fetchPublicLists(): void {
    const token = localStorage.getItem('token');
    const headers = token ? { Authorization: `Bearer ${token}` } : {};

    this.publicListService.getPublicLists(headers).subscribe({
      next: (data) => {
        this.publicLists = data.map((list) => ({
          ...list,
          destinations: list.destinations || [],
          reviews: list.reviews || [],
          averageRating: this.calculateAverageRating(list.reviews),
        }));
      },
      error: (err) => {
        console.error('Error fetching public lists:', err);
      },
    });
  }
  
  calculateAverageRating(reviews: any[]): number {
    if (!reviews || reviews.length === 0) return 0;
    const totalRating = reviews.reduce((sum, review) => sum + (review.rating || 0), 0);
    return Math.round((totalRating / reviews.length) * 10) / 10; 
  }
  


  fetchSavedLists(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('User is not authenticated. Cannot fetch saved lists.');
      return;
    }

    this.publicListService.getSavedLists(token).subscribe({
      next: (data) => {
        this.savedLists = data;
        console.log('Fetched saved lists:', this.savedLists);
      },
      error: (err) => {
        console.error('Error fetching saved lists:', err);
      },
    });
  }

  postList(): void {
    if (!this.isLoggedIn) {
      alert('You need to log in to post a list.');
      return;
    }
  
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Authentication token is missing.');
      return;
    }
  
    const selectedList = this.savedLists.find((list) => list.title === this.newListName);
    if (!selectedList) {
      alert('Selected list not found.');
      return;
    }
  
    const destinations = selectedList.destinations.map((dest: Destination) => ({
      Destination: dest.Destination,
      Country: dest.Country,
    }));
  
    const body = {
      name: this.newListName,
      destinations,
    };
  
    console.log('Posting public list:', body);
  
    this.publicListService.postPublicList(this.newListName, destinations, token).subscribe({
      next: () => {
        alert('List posted successfully!');
        this.newListName = '';
        this.newListItems = '';
        this.fetchPublicLists();
      },
      error: (err) => {
        console.error('Error posting list:', err);
        alert('Failed to post the list. Please try again.');
      },
    });
  }
  
  
  
  

  toggleSavedListsDropdown(): void {
    this.showSavedListsDropdown = !this.showSavedListsDropdown;
  }

  onSavedListSelected(): void {
    const selectedList = this.savedLists.find((list) => list.title === this.selectedSavedList);
    if (selectedList) {
      this.newListName = selectedList.title;
  
      this.newListItems = selectedList.destinations
        .map((dest: Destination) =>  `${dest.Destination} (${dest.Country})`)
        .join(', ');
  
      console.log('Selected list:', {
        title: selectedList.title,
        destinations: selectedList.destinations,
      });
    }
  }
  

  checkLoginStatus(): void {
    const token = localStorage.getItem('token');
    this.isLoggedIn = !!token;
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        this.isAdmin = payload.isAdmin || false; 
        console.log('Admin Status:', this.isAdmin); 
      } catch (error) {
        console.error('Error decoding token:', error);
        this.isAdmin = false;
      }
    }
  }


  openReviewDialog(list: any): void{
    console.log('Opening review dialog for list: ', list);
    this.selectedListForReview = list;
    this.reviewRating = 0;
    this.reviewComment = '';
    this.cdr.detectChanges();
  }
  submitReview(): void {
    if (!this.reviewRating || this.reviewRating < 1 || this.reviewRating > 5) {
      alert('Please select a rating between 1 and 5.');
      return;
    }
  
    const token = localStorage.getItem('token');
    if (!token) {
      alert('You need to log in to submit a review.');
      return;
    }
  
    const listId = this.selectedListForReview._id;
  
    console.log('Submitting review for list:', listId);
  
    this.http
      .post(
        `/api/reviews/${listId}`,
        { rating: this.reviewRating, comment: this.reviewComment },
        { headers: { Authorization: `Bearer ${token}` } }
      )
      .subscribe({
        next: () => {
          alert('Review submitted successfully.');
          this.selectedListForReview = null;
          this.fetchPublicLists();
        },
        error: (err) => {
          console.error('Error submitting review:', err.error || err.message || err);
          alert(`Failed to submit review: ${err.error?.message || 'Unknown error'}`);
        },
      });
  }

  cancelReview(): void {
    this.selectedListForReview = null;
    this.reviewRating = 0;
    this.reviewComment = '';
  }

  closeReviewModal(): void {
    this.selectedListForModal = null;
    this.showReviewsModal = false;
  }

  openReviewModal(list: any): void {
    console.log('Opening reviews modal for list:', list);
    this.selectedListForModal = list;
    this.showReviewsModal = true;
  }

  hideReview(reviewId: string): void {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in as an admin to perform this action.');
      return;
    }
  
    this.http
      .put(`/api/reviews/hide/${reviewId}`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          alert('Review hidden successfully.');
          this.fetchPublicLists(); 
        },
        error: (err) => {
          console.error('Error hiding review:', err);
        },
      });
  }

  unhideReview(reviewId: string): void {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in as an admin to perform this action.');
      return;
    }

    this.http
      .put(`/api/reviews/unhide/${reviewId}`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          alert('Review unhidden successfully.');
          this.fetchPublicLists(); 
        },
        error: (err) => console.error('Error unhiding review:', err),
      });
  }

  toggleListExpansion(listId: string): void {
    this.expandedList = this.expandedList === listId ? null : listId;
  }

}
  

