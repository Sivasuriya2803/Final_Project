import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
export interface Vehicle {
  id?: number;
  userId?: number;
  type: string;
  make: string;
  model: string;
  year: number;
  registrationNumber: string;
}
@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private apiUrl = 'http://localhost:8080/api/vehicles'; 

  constructor(private http: HttpClient) {}

  registerVehicle(vehicle: Vehicle, userId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${userId}`, vehicle);
  }

  getAllVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.apiUrl);
  }

  getUserVehicles(userId: number): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.apiUrl}/${userId}`);
  }
}
