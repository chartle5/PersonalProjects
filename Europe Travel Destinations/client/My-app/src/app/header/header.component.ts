import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isLoggedIn: boolean = false;
  nickname: string | null = null;
  isAdmin: boolean = false;

  @Output() openLogin = new EventEmitter<void>();
  @Output() pageChange = new EventEmitter<void>();

  constructor(private router: Router, private authService: AuthService) {
    this.authService.isLoggedIn$.subscribe((status) => {
      this.isLoggedIn = status;
    });

    this.authService.nickname$.subscribe((nickname) => {
      this.nickname = nickname;
    });

    this.authService.isAdmin$.subscribe((adminStatus) => {
      this.isAdmin = adminStatus; 
    });
  }

  searchPage() {
    this.pageChange.emit();
    this.router.navigate(['/search']);
  }

  listPage() {
    if (this.isLoggedIn) {
      this.pageChange.emit();
      this.router.navigate(['/lists']);
    } else {
      alert("Please log in to view your lists.");
    }
  }

  logout() {
    this.authService.logout();
    alert('You have successfully logged out.');
    this.router.navigate(['/']);
  }

  login() {
    this.openLogin.emit();
  }
}






