import { Component, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Policy } from '../policy';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { UserProfile } from '../user-profile';
import { Proposal } from '../proposal';
import { ProposalStatus } from '../proposal-status';
import { VehicleService, Vehicle as VehicleModel } from '../vehicle';
import { VehicleType } from '../vehicle-type';
import { ProposalRequest } from '../proposal-request';
import { Payment } from '../payment';
import { PaymentResponseDto } from '../payment-response-dto';

@Component({
  selector: 'app-dashboard-user',
  standalone: true,
  imports: [CommonModule, HttpClientModule,FormsModule,RouterModule, UserProfile],
  templateUrl: './dashboard-user.html',
  styleUrl: './dashboard-user.css'
})
export class DashboardUser {
  policies: any[] = [];
  proposals: ProposalStatus[] = [];
  username: string | null = null;
  fullName: string | null = null;
  userId: number = 0;
  dropdownOpen = false;
  showProfile = false;
  activeSection: 'policies' | 'proposals' | 'payments' | 'vehicles' = 'policies';
  showAddProposal = false;
  newProposal: { vehicleType: VehicleType | null; policyId: number | null } = { vehicleType: null, policyId: null };
  vehicles: VehicleModel[] = [];
  quoteGeneratedProposals: ProposalStatus[] = [];
  newVehicle: VehicleModel = {
    type: '',
    make: '',
    model: '',
    year: new Date().getFullYear(),
    registrationNumber: ''
  };
  vehicleTypes: string[] = ['CAR', 'TRUCK', 'MOTORCYCLE', 'CAMPER_VAN'];
  showAddVehicle = false;
  maxVehicleYear = new Date().getFullYear() + 1;
  paymentModalOpen = false;
  paymentModalQuote: ProposalStatus | null = null;
  paymentModalAmount: number | null = null;
  paymentProcessing = false;
  paymentError: string | null = null;

  constructor(
    private policy: Policy,
    private proposalService: Proposal,
    private vehicleService: VehicleService,
    private router: Router,
    private paymentService: Payment
  ) { }

  ngOnInit(): void {
    this.policy.getPolicies().subscribe(data => {
      this.policies = data;
    });
    const userIdStr = localStorage.getItem('userId');
    console.log('userId from localStorage:', userIdStr);
    this.userId = Number(userIdStr);
    console.log('userId as number:', this.userId);
    if (!isNaN(this.userId) && this.userId > 0) {
      this.proposalService.getUserProposals(this.userId).subscribe({
        next: (data) => this.proposals = data,
        error: (err) => console.error(err)
      });
      this.loadQuoteGeneratedProposals();
    } else {
      console.error('Invalid userId:', this.userId);
    }
    this.username = localStorage.getItem('username');
    this.fullName = localStorage.getItem('fullName');
    // Fetch vehicles
    this.loadVehicles();
  }

  loadQuoteGeneratedProposals() {
    if (this.userId > 0) {
      this.proposalService.getQuoteGeneratedProposals(this.userId).subscribe({
        next: (data) => this.quoteGeneratedProposals = data,
        error: (err) => console.error('Error loading quote-generated proposals:', err)
      });
    }
  }

  loadVehicles() {
    this.vehicleService.getUserVehicles(this.userId).subscribe(data => {
      this.vehicles = data;
    });
  }

  registerVehicle() {
    this.vehicleService.registerVehicle(this.newVehicle, this.userId).subscribe({
      next: () => {
        alert('Vehicle registered!');
        this.newVehicle = {
          type: '',
          make: '',
          model: '',
          year: new Date().getFullYear(),
          registrationNumber: ''
        };
        this.loadVehicles();
        this.closeAddVehicle();
      },
      error: () => {
        alert('Failed to register vehicle.');
      }
    });
  }

  logout() {
    this.router.navigate(['/home']);
  }

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  @HostListener('document:click', ['$event'])
  closeDropdown(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.profile-dropdown')) {
      this.dropdownOpen = false;
    }
  }

  viewProfile() {
    this.showProfile = true;
    this.dropdownOpen = false;
  }

  closeProfile() {
    this.showProfile = false;
  }

  setSection(section: 'policies' | 'proposals' | 'payments' | 'vehicles') {
    this.activeSection = section;
  }

  openAddProposal() {
    this.newProposal = { vehicleType: null, policyId: null };
    this.showAddProposal = true;
  }

  closeAddProposal() {
    this.showAddProposal = false;
  }

  submitProposal() {
    if (!this.userId || !this.newProposal.vehicleType || !this.newProposal.policyId) {
      alert('Please fill all fields.');
      return;
    }
    const proposalReq = {
      userId: this.userId,
      vehicleType: this.newProposal.vehicleType,
      policyId: this.newProposal.policyId
    } as ProposalRequest;
    this.proposalService.submitProposal(proposalReq).subscribe({
      next: (res) => {
        alert('Proposal submitted successfully!');
        this.closeAddProposal();
        // Refresh proposals
        this.proposalService.getUserProposals(this.userId).subscribe({
          next: (data) => this.proposals = data,
          error: (err) => console.error(err)
        });
      },
      error: (err) => {
        alert('Failed to submit proposal.');
        console.error(err);
      }
    });
  }

  openAddVehicle() {
    this.newVehicle = { type: '', make: '', model: '', year: new Date().getFullYear(), registrationNumber: '' };
    this.showAddVehicle = true;
  }

  closeAddVehicle() {
    this.showAddVehicle = false;
  }

  makePayment(quote: ProposalStatus) {
    this.paymentModalOpen = true;
    this.paymentModalQuote = quote;
    this.paymentModalAmount = null;
    this.paymentProcessing = false;
    this.paymentError = null;
    this.paymentService.getPremiumAmount(quote.proposalId).subscribe({
      next: (amount) => {
        this.paymentModalAmount = amount;
      },
      error: (err) => {
        this.paymentError = 'Failed to fetch premium amount.';
        this.paymentModalAmount = null;
        console.error(err);
      }
    });
  }

  closePaymentModal() {
    this.paymentModalOpen = false;
    this.paymentModalQuote = null;
    this.paymentModalAmount = null;
    this.paymentProcessing = false;
    this.paymentError = null;
  }

  confirmPayment() {
    if (!this.paymentModalQuote || this.paymentModalAmount == null) return;
    this.paymentProcessing = true;
    this.paymentError = null;
    this.paymentService.completePayment(this.paymentModalQuote.proposalId, this.paymentModalAmount).subscribe({
      next: (res: PaymentResponseDto) => {
        this.paymentProcessing = false;
        this.closePaymentModal();
        this.loadQuoteGeneratedProposals();
        // Optionally show a toast/snackbar here
      },
      error: (err) => {
        this.paymentProcessing = false;
        this.paymentError = 'Payment failed. Please try again.';
        console.error(err);
      }
    });
  }

  viewQuoteDetails(quote: ProposalStatus) {
    // TODO: Implement quote details modal or navigation
    alert(`Quote #${quote.proposalId} details:\nStatus: ${quote.status}\nSubmitted: ${quote.submittedAt}\nQuote Generated: ${quote.quoteGeneratedAt}`);
    console.log('Viewing quote details:', quote);
  }

  submitVehicle() {
    if (!this.newVehicle.type || !this.newVehicle.make || !this.newVehicle.model || !this.newVehicle.year || !this.newVehicle.registrationNumber) {
      alert('Please fill all fields.');
      return;
    }
    this.vehicleService.registerVehicle(this.newVehicle, this.userId).subscribe({
      next: (res) => {
        alert('Vehicle added successfully!');
        this.closeAddVehicle();
        // Refresh vehicles
        this.vehicleService.getUserVehicles(this.userId).subscribe({
          next: (data) => this.vehicles = data,
          error: (err) => console.error(err)
        });
      },
      error: (err) => {
        alert('Failed to add vehicle.');
        console.error(err);
      }
    });
  }
}
