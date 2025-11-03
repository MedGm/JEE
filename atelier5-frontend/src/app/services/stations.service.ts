import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateStationRequest, Station, StationPage } from '../models/station';

const API_BASE = '/api';

@Injectable({ providedIn: 'root' })
export class StationsService {
  private readonly http = inject(HttpClient);

  list(page = 0, size = 20): Observable<StationPage> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<StationPage>(`${API_BASE}/stations`, { params });
  }

  getById(id: number): Observable<Station> {
    return this.http.get<Station>(`${API_BASE}/stations/${id}`);
  }

  create(body: CreateStationRequest): Observable<Station> {
    return this.http.post<Station>(`${API_BASE}/stations`, body);
  }

  update(id: number, body: CreateStationRequest): Observable<Station> {
    return this.http.put<Station>(`${API_BASE}/stations/${id}`, body);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE}/stations/${id}`);
  }
}


