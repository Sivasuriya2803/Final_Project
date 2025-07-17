import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProposalRequest } from './proposal-request';
import { ProposalStatus } from './proposal-status';
export { ProposalStatus };

@Injectable({
  providedIn: 'root'
})
export class Proposal {

private baseUrl = 'http://localhost:8080/api/proposals';

  constructor(private http: HttpClient) {}

  submitProposal(proposal: ProposalRequest): Observable<ProposalStatus> {
    return this.http.post<ProposalStatus>(`${this.baseUrl}`, proposal);
  }

  getUserProposals(userId: number): Observable<ProposalStatus[]> {
    return this.http.get<ProposalStatus[]>(`${this.baseUrl}/${userId}`);
  }
  
  getAllProposals(): Observable<ProposalStatus[]> {
    return this.http.get<ProposalStatus[]>('http://localhost:8080/api/officer/proposals');
  }
  
  getQuoteGeneratedProposals(userId: number): Observable<ProposalStatus[]> {
    return this.http.get<ProposalStatus[]>(
      `${this.baseUrl}/quote-generated?userId=${userId}`
    );
  }

  reviewProposal(proposalId: number, approve: boolean): Observable<any> {
    return this.http.put(
      `http://localhost:8080/api/officer/review/${proposalId}?approve=${approve}`,
      {}
    );
  }
  
}
