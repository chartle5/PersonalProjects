import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {
  users: any[] = [];
  reviews: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('No token found');
      return;
    }

    this.http
      .get<any[]>('/api/admin/users', {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: (data) => {
          this.users = data;
        },
        error: (error) => console.error('Error fetching users:', error),
      });
  }

  deactivateUser(userId: string): void {
    const token = localStorage.getItem('token');
    this.http
      .put(`/api/admin/deactivate-user/${userId}`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          this.fetchUsers();
          alert('User has been deactivated.');
        },
        error: (error) => console.error('Error deactivating user:', error),
      });
  }

  reactivateUser(userId: string): void {
    const token = localStorage.getItem('token');
    this.http
      .put(`/api/admin/reactivate-user/${userId}`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          this.fetchUsers();
          alert('User has been reactivated.');
        },
        error: (error) => console.error('Error reactivating user:', error),
      });
  }

  grantAdmin(userId: string): void {
    const token = localStorage.getItem('token');
    this.http
      .put(`/api/admin/grant-admin/${userId}`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe({
        next: () => {
          this.fetchUsers();
          alert('User has been granted admin privileges.');
        },
        error: (error) => console.error('Error granting admin privileges:', error),
      });
  }


}


