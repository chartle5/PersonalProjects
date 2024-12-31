import { Component, Output, EventEmitter } from '@angular/core';
import { RegisterService } from './register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  nickname: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';

  @Output() registrationSuccess = new EventEmitter<void>();
  @Output() closeRegister = new EventEmitter<void>();

  constructor(private registerService: RegisterService) {}

  onSubmit() {
    if (!this.validateEmail(this.email)) {
      alert('Invalid email format. Please include "@" in your email.');
      return;
    }

    if (this.password !== this.confirmPassword) {
      alert('Passwords do not match.');
      return;
    }
    if (!this.email){
      alert("Please enter an email");
    }
    if (!this.nickname) {
      alert("Please enter a nickname");
    }
    if (!this.password){
      alert("Please enter a password");
    }


    const userData = {
      nickname: this.nickname,
      email: this.email,
      password: this.password
    };

    this.registerService.register(userData).subscribe({
      next: (response) => {
        if(!response.succes){
          alert(response.message);
          return;
        }
        console.log('User registered successfully', response);
        alert(`${this.nickname}, you have registered successfully! Please check your email (${this.email}) to verify your account.`);
        this.registrationSuccess.emit();
        this.onCloseModal();
      },
      error: (error) => {
        console.error('Error registering user', error);
      }
    });
  }

  onCloseModal(){
    console.log("Closing Modal");
    this.closeRegister.emit();
  }

  validateEmail(email: string): boolean {
    return email.includes('@'); 
  }
}
