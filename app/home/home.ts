import { CommonModule } from '@angular/common';
import { Component, OnInit, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {
  isMobileMenuOpen = false;

  ngOnInit() {
    // Initialize any component logic
  }

  @HostListener('window:scroll')
  onWindowScroll() {
    const navbar = document.querySelector('.navbar');
    if (navbar) {
      if (window.scrollY > 50) {
        navbar.classList.add('scrolled');
      } else {
        navbar.classList.remove('scrolled');
      }
    }
  }

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
    const navMenu = document.querySelector('.nav-menu');
    if (navMenu) {
      navMenu.classList.toggle('active');
    }
  }

  closeMobileMenu() {
    this.isMobileMenuOpen = false;
    const navMenu = document.querySelector('.nav-menu');
    if (navMenu) {
      navMenu.classList.remove('active');
    }
  }

  scrollToSection(sectionId: string) {
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
      this.closeMobileMenu();
    }
  }

  onSubmitQuote(event: Event) {
    event.preventDefault();
    // Handle quote form submission
    alert('Thank you for your interest! We will contact you soon with a personalized quote.');
  }
}
