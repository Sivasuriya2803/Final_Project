import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { RegisterService, RegisterData } from '../register';

@Component({
  selector: 'app-register',
  imports: [FormsModule, CommonModule, RouterModule, HttpClientModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
  registerData = {
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    name: '',
    address: '',
    dob: '',
    aadhaarNumber: '',
    panNumber: '',
    phoneNumber: ''
  };

  isLoading = false;

  constructor(private router: Router, private registerService: RegisterService) {}

  registerUser() {
    // Basic validation
    if (!this.registerData.username || !this.registerData.email || !this.registerData.password) {
      alert('Please fill in all required fields');
      return;
    }

    if (this.registerData.password !== this.registerData.confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    if (this.registerData.password.length < 6) {
      alert('Password must be at least 6 characters long');
      return;
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.registerData.email)) {
      alert('Please enter a valid email address');
      return;
    }

    // Aadhaar validation (if provided)
    if (this.registerData.aadhaarNumber && this.registerData.aadhaarNumber.length !== 12) {
      alert('Aadhaar number must be 12 digits');
      return;
    }

    // PAN validation (if provided)
    if (this.registerData.panNumber && this.registerData.panNumber.length !== 10) {
      alert('PAN number must be 10 characters');
      return;
    }

    // Phone validation (if provided)
    if (this.registerData.phoneNumber && this.registerData.phoneNumber.length !== 10) {
      alert('Phone number must be 10 digits');
      return;
    }

    this.isLoading = true;

    // Prepare data for API call
    const userData: RegisterData = {
      username: this.registerData.username,
      email: this.registerData.email,
      password: this.registerData.password,
      name: this.registerData.name,
      address: this.registerData.address || undefined,
      dob: this.registerData.dob || undefined,
      aadhaarNumber: this.registerData.aadhaarNumber || undefined,
      panNumber: this.registerData.panNumber || undefined,
      phoneNumber: this.registerData.phoneNumber || undefined
    };

    // Call the registration service
    this.registerService.registerUser(userData).subscribe({
      next: (response) => {
        this.isLoading = false;
        alert('Registration Successful! Welcome to AutoCare Insurance.');
        this.router.navigate(['/user-login']);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Registration error:', error);
        
        if (error.status === 409) {
          alert('Username or email already exists. Please choose different credentials.');
        } else if (error.status === 400) {
          alert('Invalid data provided. Please check your information.');
        } else {
          alert('Registration failed. Please try again later.');
        }
      }
    });
  }

  clearForm() {
    this.registerData = {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      name: '',
      address: '',
      dob: '',
      aadhaarNumber: '',
      panNumber: '',
      phoneNumber: ''
    };
  }
}
