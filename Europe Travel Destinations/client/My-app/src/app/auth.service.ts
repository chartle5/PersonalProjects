import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api';
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  private nicknameSubject = new BehaviorSubject<string>('Guest');
  private isAdminSubject = new BehaviorSubject<boolean>(false); 

  constructor(private http: HttpClient) {
    this.checkLoginStatus();
  }

  get isLoggedIn$(): Observable<boolean> {
    return this.isLoggedInSubject.asObservable();
  }

  get nickname$(): Observable<string> {
    return this.nicknameSubject.asObservable();
  }

  get isAdmin$(): Observable<boolean> { 
    return this.isAdminSubject.asObservable();
  }

  login(data: { email: string; password: string }): Observable<any> {
    return new Observable(observer => {
      this.http.post(`${this.apiUrl}/login`, data).subscribe({
        next: (response: any) => {
          const token = response.token;
          localStorage.setItem('token', token);
          this.updateLoginStateFromToken(token);
          observer.next(response);
        },
        error: (err) => {
          observer.error(err);
        }
      });
    });
  }
  

  register(nickname: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { nickname, email, password });
  }

  logout(): void {
    localStorage.removeItem('token');
    this.isLoggedInSubject.next(false);
    this.nicknameSubject.next('Guest');
    this.isAdminSubject.next(false); 
  }

  private checkLoginStatus(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.updateLoginStateFromToken(token);
    } else {
      this.logout();
    }
  }

  private updateLoginStateFromToken(token: string): void {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const isTokenValid = this.validateTokenExpiration(payload.exp);
      if (isTokenValid) {
        this.isLoggedInSubject.next(true);
        this.nicknameSubject.next(payload.nickname || 'Guest');
        this.isAdminSubject.next(payload.isAdmin || false); 
      } else {
        this.logout();
      }
    } catch (err) {
      console.error('Error decoding token:', err);
      this.logout();
    }
  }

  private validateTokenExpiration(expiration: number): boolean {
    const currentTime = Math.floor(Date.now() / 1000);
    return expiration > currentTime;
  }
}


