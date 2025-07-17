import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Auth } from './auth';
import { UserProfileDto } from './user-profile-dto';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css'
})
export class UserProfile implements OnInit {
  @Input() visible = false;
  @Output() close = new EventEmitter<void>();
  profile: UserProfileDto | null = null;
  loading = false;
  error: string | null = null;

  constructor(private auth: Auth) {}

  ngOnInit() {
    if (this.visible) {
      this.fetchProfile();
    }
  }

  ngOnChanges() {
    if (this.visible) {
      this.fetchProfile();
    }
  }

  fetchProfile() {
    this.loading = true;
    this.error = null;
    this.auth.getProfile().subscribe({
      next: (profile) => {
        this.profile = profile;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load profile.';
        this.loading = false;
      }
    });
  }

  onClose() {
    this.close.emit();
  }
} 