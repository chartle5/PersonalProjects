import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.css'],
})
export class ListsComponent implements OnInit {
  savedLists: { title: string; destinations: { Destination: string; Country: string }[] }[] = [];
  workingList: { Destination: string; Country: string }[] = [];
  nickname: string = 'Guest';
  selectedList: { title: string; destinations: { Destination: string; Country: string }[] } | null = null;
  expandedDestination: { Destination: string; Country: string } | null = null;
  selectedSavedListForAdd: string | null = null;

  constructor(private authService: AuthService, private http: HttpClient) {}

  ngOnInit(): void {
    this.authService.nickname$.subscribe((nickname) => {
      this.nickname = nickname;
    });

    this.fetchSavedLists();
    this.loadWorkingList();
  }

  fetchSavedLists(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('No token found. Unable to fetch saved lists.');
      return;
    }
  
    this.http
      .get<{ title: string; destinations: any[] }[]>(
        '/api/lists',
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )
      .subscribe({
        next: (lists) => {
          this.savedLists = lists.map((list) => ({
            title: list.title,
            destinations: list.destinations.map((dest) =>
              typeof dest === 'string'
                ? { Destination: dest, Country: 'Unknown' }
                : dest
            ),
          }));
        },
        error: (error) => {
          console.error('Error fetching lists:', error);
          alert('Failed to fetch your saved lists. Please log in again.');
        },
      });
  }

  loadWorkingList(): void {
    const savedWorkingList = localStorage.getItem('workingList');
    this.workingList = savedWorkingList ? JSON.parse(savedWorkingList) : [];
  }

  saveWorkingList(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in to save your list.');
      return;
    }
  
    const listName = prompt('Enter a name for your list:');
    if (!listName) {
      return;
    }
  
    const isDuplicate = this.savedLists.some((list) => list.title.toLowerCase() === listName.toLowerCase());
    if (isDuplicate) {
      alert('List name must be unique. Please choose a different name.');
      return;
    }
  
    const payload = {
      destinations: this.workingList,
    };
  
    this.http
      .post(
        `/api/lists/${listName}`,
        payload,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )
      .subscribe({
        next: () => {
          alert(`List '${listName}' saved successfully.`);
          this.workingList = [];
          localStorage.removeItem('workingList');
          this.fetchSavedLists();
        },
        error: (error) => {
          console.error('Error saving list:', error);
          alert('Failed to save the list.');
        },
      });
  }

  deleteList(title: string): void {
    const token = localStorage.getItem('token');
    if (!token) {
      console.warn('No token found. Unable to delete list.');
      return;
    }

    this.http
      .delete(`/api/lists/${title}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          alert(`List '${title}' deleted successfully.`);
          this.savedLists = this.savedLists.filter((list) => list.title !== title);
        },
        error: (error) => {
          console.error('Error deleting list:', error);
          alert('Failed to delete the list.');
        },
      });
  }

  editList(): void {
    if (!this.selectedList) {
      alert('No list selected for editing.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in to edit your list.');
      return;
    }

    const newName = prompt('Enter a new name for this list:', this.selectedList.title);
    if (!newName || newName === this.selectedList.title) {
      alert('No changes made to the list name.');
      return;
    }

    const payload = {
      title: newName,
      destinations: this.selectedList.destinations,
    };

    this.http
      .put(`/api/lists/${this.selectedList.title}`, payload, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          alert(`List renamed to '${newName}' successfully.`);
          this.fetchSavedLists();
          this.clearSelectedList();
        },
        error: (error) => {
          console.error('Error renaming list:', error);
          alert('Failed to rename the list.');
        },
      });
  }

  removeDestinationFromList(destination: { Destination: string; Country: string }): void {
    if (!this.selectedList) {
      alert('No list selected for editing.');
      return;
    }

    this.selectedList.destinations = this.selectedList.destinations.filter((dest) => dest !== destination);

    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in to edit your list.');
      return;
    }

    const payload = { destinations: this.selectedList.destinations };

    this.http
      .put(`/api/lists/${this.selectedList.title}`, payload, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          alert(`Destination removed successfully.`);
          this.fetchSavedLists();
        },
        error: (error) => {
          console.error('Error removing destination:', error);
          alert('Failed to remove the destination.');
        },
      });
  }

  removeFromWorkingList(destination: { Destination: string; Country: string }): void {
    this.workingList = this.workingList.filter((item) => item !== destination);
    localStorage.setItem('workingList', JSON.stringify(this.workingList));
  }

  viewList(list: { title: string; destinations: { Destination: string; Country: string }[] }): void {
    this.selectedList = { ...list };
  }

  clearSelectedList(): void {
    this.selectedList = null;
  }

  toggleDetails(destination: { Destination: string; Country: string }): void {
    this.expandedDestination =
      this.expandedDestination === destination ? null : destination;
  }

  searchOnDDG(destination: { Destination: string }): void {
    const query = encodeURIComponent(destination.Destination);
    window.open(`https://duckduckgo.com/?q=${query}`, '_blank');
  }

  addToExistingList(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in to modify your list.');
      return;
    }
  
    if (!this.selectedSavedListForAdd) {
      alert('Please select a list to add destinations.');
      return;
    }
  
    const existingList = this.savedLists.find((list) => list.title === this.selectedSavedListForAdd);
    if (!existingList) {
      alert('List not found. Please refresh the page.');
      return;
    }
  
    const uniqueDestinations = this.workingList.filter(
      (destination) =>
        !existingList.destinations.some(
          (existing) => existing.Destination === destination.Destination
        )
    );
  
    const payload = { destinations: [...existingList.destinations, ...uniqueDestinations] };
  
    const listNameEncoded = encodeURIComponent(this.selectedSavedListForAdd);
  
    this.http
      .put(`/api/lists/${listNameEncoded}`, payload, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          alert(`Destinations added to '${this.selectedSavedListForAdd}' successfully.`);
          this.workingList = [];
          localStorage.removeItem('workingList');
          this.fetchSavedLists();
        },
        error: (error) => {
          console.error('Error adding to list:', error);
          alert('Failed to add destinations to the list.');
        },
      });
  }
  
  
  
}


