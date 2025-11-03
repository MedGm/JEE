import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Carburant, CarburantPage, CreateCarburantRequest } from '../models/carburant';

const API_BASE = '/api';

@Injectable({ providedIn: 'root' })
export class CarburantsService {
  private readonly http = inject(HttpClient);

  list(page = 0, size = 20): Observable<CarburantPage> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<CarburantPage>(`${API_BASE}/carburants`, { params });
  }

  getById(id: number): Observable<Carburant> {
    return this.http.get<Carburant>(`${API_BASE}/carburants/${id}`);
  }

  create(body: CreateCarburantRequest): Observable<Carburant> {
    return this.http.post<Carburant>(`${API_BASE}/carburants`, body);
  }

  update(id: number, body: CreateCarburantRequest): Observable<Carburant> {
    return this.http.put<Carburant>(`${API_BASE}/carburants/${id}`, body);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE}/carburants/${id}`);
  }
}


