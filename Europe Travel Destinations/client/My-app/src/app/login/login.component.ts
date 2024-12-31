import { Component, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = ''; 
  password: string = '';

  @Output() closeModal = new EventEmitter<void>();
  @Output() loginSuccess = new EventEmitter<void>();
  @Output() switchToRegister = new EventEmitter<void>();

  constructor(private authService: AuthService) {}

  onSubmit() {
    const loginData = {
      email: this.email,
      password: this.password,
    };
  
    this.authService.login(loginData).subscribe({
      next: (response) => {
        console.log('Login successful:', response);
        alert('Login successful!');
        this.loginSuccess.emit();
        this.closeLoginModal();
      },
      error: (error) => {
        console.error('Login failed', error);
        alert(error.error?.message || 'Login failed. Please try again.');
      },
    });
  }


  closeLoginModal() {
    console.log("closing modal");
    this.closeModal.emit(); 
  }

  onSwitchToRegister() {
    console.log("Switching to Register Modal");
    this.switchToRegister.emit(); 
  }
}


