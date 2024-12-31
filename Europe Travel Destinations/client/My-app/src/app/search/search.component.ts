import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ListService } from '../lists/list.service';
import Fuse from 'fuse.js';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit{
  searchType: string = 'Destination';
  numberOfResults: number = 5;
  searchBar: string = '';
  searchResults: any[] = [];
  totalResults: number = 0;
  currentPage: number = 1;
  selectedDestination: any = null;
  expandedDestination: any = null;
  searchMade: boolean = false;
  startIndex: number = 0;
  endIndex: number = 0;
  workingList: any[] = [];

  constructor(private http: HttpClient, private listService: ListService ) {}
  

  ngOnInit(): void {}

  normalizeString(str: string): string {
    return (str || '').trim().toLowerCase().replace(/\s+/g, ' ');
  }

  onSearch(isNewSearch: boolean = true): void {
    const searchValue = this.normalizeString(this.searchBar || '');
  
    if (!searchValue) {
      alert('Please enter a search term.');
      return;
    }
  
    if (isNewSearch) {
      this.currentPage = 1;
    }
  
    const params: any = {
      n: this.numberOfResults.toString(),
      page: this.currentPage.toString(),
      query: searchValue,
      searchType: this.searchType,
    };
  
    this.http.get<any>('/search', { params }).subscribe({
      next: (response) => {
        this.searchResults = response.results;
        this.totalResults = response.totalResults;
        this.searchMade = true;
  
        this.updatePagination();
      },
      error: (error) => {
        console.error('Error fetching search results:', error);
        alert('Failed to fetch search results.');
      },
    });
  }
  
  onNextPage(): void {
    if (this.currentPage * this.numberOfResults >= this.totalResults) {
      alert('You are on the last page.');
      return;
    }
    this.currentPage++;
    this.onSearch(false); 
  }
  
  onPrevPage(): void {
    if (this.currentPage <= 1) {
      alert('You are on the first page.');
      return;
    }
    this.currentPage--;
    this.onSearch(false); 
  }
  
  onFirstPage(): void {
    this.currentPage = 1;
    this.onSearch(false); 
  }
  
  onLastPage(): void {
    this.currentPage = Math.ceil(this.totalResults / this.numberOfResults);
    this.onSearch(false); 
  }

  searchOnDDG(destination: any) {
    const query = encodeURIComponent(destination.Destination);
    window.open(`https://duckduckgo.com/?q=${query}`, '_blank');
  }
  
  viewDetails(destination: any) {
    this.selectedDestination = destination;
    console.log('Viewing details for:', destination);
  }

  closeDetails(): void {
    this.selectedDestination = null;
  }

  getObjectKeys(obj: any): string[] {
    return Object.keys(obj); 
  }

  toggleDetails(destination: any): void {
    this.expandedDestination =
      this.expandedDestination === destination ? null : destination;
      this.viewDetails(this.expandedDestination);
  }


  addToWorkingList(destination: any): void {
    if (!this.workingList.some((item) => item.Destination === destination.Destination)) {
      this.workingList.push(destination);
      localStorage.setItem('workingList', JSON.stringify(this.workingList)); 
      alert(`${destination.Destination} added to your working list!`);
    } else {
      alert(`${destination.Destination} is already in your working list.`);
    }
  }
  
  removeFromWorkingList(destination: any): void {
    this.workingList = this.workingList.filter((item) => item !== destination);
    localStorage.setItem('workingList', JSON.stringify(this.workingList));
    alert(`${destination.Destination} removed from your working list.`);
  }
   

  saveWorkingList(): void {
    const listName = prompt('Enter a name for your list:');
    if (!listName) {
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in to save lists.');
      return;
    }

    this.http.post(`/api/lists/${listName}/add`, 
      { destinations: this.workingList.map(item => item.destinationId) },
      { headers: { Authorization: token } }
    ).subscribe({
      next: () => {
        alert(`List '${listName}' saved successfully!`);
        this.workingList = [];
      },
      error: (err) => {
        console.error('Error saving list:', err);
        alert('Failed to save list.');
      }
    });
  }

  getFieldForSearchType(): string {
    switch (this.searchType) {
      case 'Destination':
        return 'destinationName';
      case 'Region':
        return 'region';
      case 'Country':
        return 'country';
      default:
        return 'destinationName';
    }
  }

  
  updatePagination(): void {
    this.startIndex = (this.currentPage - 1) * this.numberOfResults + 1;
    this.endIndex = Math.min(this.startIndex + this.searchResults.length - 1, this.totalResults);
  }
}




