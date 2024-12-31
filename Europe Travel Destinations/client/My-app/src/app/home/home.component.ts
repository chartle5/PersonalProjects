import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  isLoggedIn: boolean = false; 
  nickname: string = 'Guest';

  constructor(public authService: AuthService) {}

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe((status) => {
      this.isLoggedIn = status;
    });

    this.authService.nickname$.subscribe((nickname) => {
      console.log('Updated nickname:', nickname);
      this.nickname = nickname || 'Guest';
    });
  }

}
