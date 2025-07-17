import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentResponseDto } from './payment-response-dto';

@Injectable({
  providedIn: 'root'
})
export class Payment {
  private baseUrl = 'http://localhost:8080/api/payments';

  constructor(private http: HttpClient) {}

  // Get the premium amount for a proposal
  getPremiumAmount(proposalId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/premium-amount?proposalId=${proposalId}`);
  }

  // Complete the payment after user confirms
  completePayment(proposalId: number, paymentAmount: number): Observable<PaymentResponseDto> {
    const payload = { proposalId, paymentAmount };
    return this.http.post<PaymentResponseDto>(`${this.baseUrl}/complete`, payload);
  }

  // Get all completed payments
  getCompletedPayments(): Observable<PaymentResponseDto[]> {
    return this.http.get<PaymentResponseDto[]>(`${this.baseUrl}/completed`);
  }
}
