import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{

  title = 'Europe Travel Destinations';
  showLoginModal: boolean = false;
  showRegisterModal: boolean = false;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      if (params['action'] === 'login') {
        this.showLoginModal = true;
      }
    });
  }

  openLoginDialog() {
    console.log("Modal open");
    this.showLoginModal = true;
    this.showRegisterModal = false;
  }

  openRegisterDialog(){
    this.showRegisterModal = true;
    this.showLoginModal = false;
  }

  closeLoginDialog() {
    console.log("Closing from app component");
    this.showLoginModal = false;

  }

  closeRegisterDialog(){
    console.log("Closing modal from parent");
    this.showRegisterModal = false;
    this.showLoginModal = true;
  }

  newPage(){
    if (this.showLoginModal) {
      this.showLoginModal = false;
    }
  }
  onLoginSuccess() {
    this.closeLoginDialog();
  }

  onRegistrationSuccess(){
    this.closeRegisterDialog();
    console.log("closing register modal from parent");

  }
}
