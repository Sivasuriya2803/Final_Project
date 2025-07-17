import { Component, HostListener, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Policy } from '../policy';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { UserProfile } from '../user-profile';
import { VehicleService, Vehicle } from '../vehicle';
import { Proposal, ProposalStatus } from '../proposal';
import { Payment } from '../payment';
import { PaymentResponseDto } from '../payment-response-dto';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-dashboard-officer',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule, RouterModule, UserProfile, MatTableModule, MatPaginatorModule],
  templateUrl: './dashboard-officer.html',
  styleUrl: './dashboard-officer.css'
})
export class DashboardOfficer {
  policies: any[] = [];
  vehicles: Vehicle[] = [];
  proposals: ProposalStatus[] = [];
  username: string | null = null;
  fullName: string | null = null;
  dropdownOpen = false;
  showProfile = false;
  activeSection: 'policies' | 'proposals' | 'vehicles' | 'payments' = 'policies';
  editModalOpen = false;
  editPolicyData: any = null;
  isAddMode = false;
  completedPayments: PaymentResponseDto[] = [];
  paymentsDataSource = new MatTableDataSource<PaymentResponseDto>([]);
  paymentDisplayedColumns: string[] = ['paymentId', 'proposalId', 'paymentAmount', 'paymentDate', 'paymentStatus'];
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private policy: Policy, private vehicleService: VehicleService, private proposalService: Proposal, private router: Router, private paymentService: Payment) { }

  ngOnInit(): void {
    this.policy.getPolicies().subscribe(data => {
      this.policies = data;
    });
    this.username = localStorage.getItem('username');
    this.fullName = localStorage.getItem('fullName');
    this.loadVehicles();
    this.loadProposals();
    this.loadCompletedPayments();
  }

  loadVehicles() {
    this.vehicleService.getAllVehicles().subscribe(data => {
      this.vehicles = data;
    });
  }

  loadProposals() {
    this.proposalService.getAllProposals().subscribe(data => {
      this.proposals = data;
    });
  }

  reviewProposal(proposalId: number, approve: boolean) {
    this.proposalService.reviewProposal(proposalId, approve).subscribe((response) => {
      alert(response.message);
      this.loadProposals();
    });
  }

  logout() {
    this.router.navigate(['/home']);
  }

  setSection(section: 'policies' | 'proposals' | 'vehicles' | 'payments') {
    this.activeSection = section;
    if (section === 'vehicles') {
      this.loadVehicles();
    }
    if (section === 'proposals') {
      this.loadProposals();
    }
    if (section === 'payments') {
      this.loadCompletedPayments();
    }
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

  editPolicy(policy: any) {
    // For demo: prompt for new name/description/basePremium/addOns
    const name = prompt('Edit Policy Name:', policy.name);
    if (name === null) return;
    const description = prompt('Edit Description:', policy.description);
    if (description === null) return;
    const basePremiumStr = prompt('Edit Base Premium:', policy.basePremium);
    if (basePremiumStr === null) return;
    const basePremium = parseFloat(basePremiumStr);
    if (isNaN(basePremium)) { alert('Invalid premium'); return; }
    const addOns = prompt('Edit Add-ons (comma separated):', policy.addOns);
    if (addOns === null) return;
    const updated = { ...policy, name, description, basePremium, addOns };
    this.policy.updatePolicy(policy.id, updated).subscribe(() => {
      alert('Policy updated!');
      this.refreshPolicies();
    });
  }

  deactivatePolicy(policy: any) {
    if (!confirm('Are you sure you want to deactivate this policy?')) return;
    const updated = { ...policy, isActive: false };
    this.policy.updatePolicy(policy.id, updated).subscribe(() => {
      alert('Policy deactivated!');
      this.refreshPolicies();
    });
  }

  refreshPolicies() {
    this.policy.getPolicies().subscribe(data => {
      this.policies = data;
    });
  }

  openAddModal() {
    this.isAddMode = true;
    this.editPolicyData = {
      name: '',
      description: '',
      basePremium: '',
      addOns: '',
      isActive: true
    };
    this.editModalOpen = true;
  }

  openEditModal(policy: any) {
    this.isAddMode = false;
    this.editPolicyData = { ...policy };
    this.editModalOpen = true;
  }

  closeEditModal() {
    this.editModalOpen = false;
    this.editPolicyData = null;
  }

  submitEditPolicy() {
    const updated = { ...this.editPolicyData };
    if (this.isAddMode) {
      this.policy.addPolicy(updated).subscribe(() => {
        this.refreshPolicies();
        this.closeEditModal();
      });
    } else {
      this.policy.updatePolicy(updated.id, updated).subscribe(() => {
        this.refreshPolicies();
        this.closeEditModal();
      });
    }
  }

  loadCompletedPayments() {
    this.paymentService.getCompletedPayments().subscribe(data => {
      this.completedPayments = data;
      this.paymentsDataSource.data = data;
      this.paymentsDataSource.paginator = this.paginator;
    });
  }
}
