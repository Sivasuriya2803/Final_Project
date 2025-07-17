import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PolicyDto } from './policy-dto';

@Injectable({
  providedIn: 'root'
})
export class Policy {
  private apiUrl = 'http://localhost:8080/api/policies';

  constructor(private http: HttpClient) { }

  getPolicies(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  
  addPolicy(policy: PolicyDto): Observable<any> {
    return this.http.post<any>(this.apiUrl, policy);
  }

  updatePolicy(id: number, policy: PolicyDto): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, policy);
  }

  deletePolicy(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

}
