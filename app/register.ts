import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface RegisterData {
  username: string;
  email: string;
  password: string;
  name: string;
  address?: string;
  dob?: string;
  aadhaarNumber?: string;
  panNumber?: string;
  phoneNumber?: string;
}

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private apiUrl = "http://localhost:8080/api/auth";

  constructor(private _http: HttpClient) {}

  // Register new user with proper headers
  registerUser(userData: RegisterData): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
      'Access-Control-Allow-Headers': 'Origin, Content-Type, Accept, Authorization, X-Request-With'
    });

    return this._http.post(`${this.apiUrl}/register`, userData, { headers });
  }

  // Alternative endpoint if the above doesn't work
  registerUserAlternative(userData: RegisterData): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this._http.post(`http://localhost:8080/register`, userData, { headers });
  }

  // Check if username exists
  checkUsername(username: string): Observable<any> {
    return this._http.get(`${this.apiUrl}/check-username/${username}`);
  }

  // Check if email exists
  checkEmail(email: string): Observable<any> {
    return this._http.get(`${this.apiUrl}/check-email/${email}`);
  }

  // Get registration form data (if needed)
  getRegistrationData(): Observable<any> {
    return this._http.get(`${this.apiUrl}/registration-data`);
  }
}
