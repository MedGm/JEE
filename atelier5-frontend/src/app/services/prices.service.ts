import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreatePriceRequest, PriceEntry } from '../models/price';

const API_BASE = '/api';

@Injectable({ providedIn: 'root' })
export class PricesService {
  private readonly http = inject(HttpClient);

  addPrice(stationId: number, body: CreatePriceRequest): Observable<PriceEntry> {
    return this.http.post<PriceEntry>(`${API_BASE}/stations/${stationId}/prices`, body);
  }

  listByStation(stationId: number, from?: string, to?: string): Observable<PriceEntry[]> {
    let params = new HttpParams();
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);
    return this.http.get<PriceEntry[]>(`${API_BASE}/stations/${stationId}/prices`, { params });
  }
}


