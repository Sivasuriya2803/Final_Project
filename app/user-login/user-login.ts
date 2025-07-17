import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../auth';
import { Router, RouterModule } from '@angular/router';
 

@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './user-login.html',
  styleUrl: './user-login.css'
})
export class UserLogin {
  username = '';
  password = '';
  slideIndex = 0;

  constructor(private authService: Auth, private router: Router) {
    setInterval(() => {
      this.slideIndex = (this.slideIndex + 1) % 2;
    }, 2500);
  }

  login() {
    this.authService.login({ username: this.username, password: this.password })
      .subscribe((response: any) => {
        this.authService.saveToken(response.token);
        localStorage.setItem('userId', response.userId);
        this.router.navigate(['/dashboard-User']);
      }, error => {
        alert('Login failed!');
      });
  }
}