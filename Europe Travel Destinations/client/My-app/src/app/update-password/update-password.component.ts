import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent {
  currentPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';

  constructor(private http: HttpClient) {}

  onUpdatePassword() {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Please log in to update your password.');
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      alert('New passwords do not match.');
      return;
    }

    if (!this.currentPassword){
      alert('Incorrect current password. Please try again');
    }

    const payload = {
      currentPassword: this.currentPassword,
      newPassword: this.newPassword,
      confirmPassword: this.confirmPassword
    };

    this.http
      .post('/api/users/update-password', payload, {
        headers: { Authorization: `Bearer ${token}` }
      })
      .subscribe({
        next: () => {
          alert('Password updated successfully.');
          this.currentPassword = '';
          this.newPassword = '';
          this.confirmPassword = '';
        },
        error: (error) => {
          console.error('Error updating password:', error);
          if (error.status === 401) {
            alert('Current password is incorrect.');
          } else {
            alert('Failed to update password. Please try again later.');
          }
        }
      });
  }
}

