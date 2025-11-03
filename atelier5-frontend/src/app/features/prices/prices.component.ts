import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PricesService } from '../../services/prices.service';
import { PriceEntry } from '../../models/price';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf, DatePipe, DecimalPipe } from '@angular/common';
import { CarburantsService } from '../../services/carburants.service';
import { Carburant } from '../../models/carburant';

@Component({
  selector: 'app-prices',
  standalone: true,
  imports: [RouterLink, FormsModule, NgFor, NgIf, DatePipe, DecimalPipe],
  template: `
    <div class="page-header">
      <h1>Historique des Prix - Station #{{ stationId }}</h1>
      <a routerLink="/stations" class="btn btn-secondary">← Retour aux stations</a>
    </div>

    <div class="card mb-3">
      <div class="card-header">
        <h3 class="card-title">Ajouter un nouveau prix</h3>
      </div>
      <form (ngSubmit)="addPrice()" #f="ngForm" class="flex flex-wrap gap-2" style="align-items: end;">
        <div class="form-group" style="flex: 1 1 200px;">
          <label>Carburant</label>
          <select name="carburantId" class="form-control" [(ngModel)]="carburantId" required>
            <option [ngValue]="null" disabled>-- Choisir un carburant --</option>
            <option *ngFor="let c of carburants()" [ngValue]="c.id">{{ c.nom }}</option>
          </select>
        </div>
        <div class="form-group" style="flex: 1 1 150px;">
          <label>Date</label>
          <input type="date" name="date" class="form-control" [(ngModel)]="date" required />
        </div>
        <div class="form-group" style="flex: 1 1 120px;">
          <label>Prix (DH)</label>
          <input 
            type="number" 
            step="0.01" 
            min="0" 
            name="prix" 
            class="form-control" 
            [(ngModel)]="prix" 
            required 
            placeholder="0.00"
          />
        </div>
        <div class="form-group" style="flex: 0 0 auto;">
          <button type="submit" class="btn btn-primary" [disabled]="loading()">
            {{ loading() ? 'Ajout...' : 'Ajouter' }}
          </button>
        </div>
      </form>
    </div>

    <div *ngIf="error()" class="alert alert-error">{{ error() }}</div>

    <div *ngIf="loading() && prices().length === 0" class="loading">Chargement...</div>

    <div *ngIf="!loading() || prices().length > 0" class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Carburant</th>
            <th>Prix (DH)</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let p of prices()">
            <td><span class="badge badge-primary">{{ p.id }}</span></td>
            <td>{{ p.date }}</td>
            <td><span class="badge badge-success">{{ carburantName(p.carburantId) }}</span></td>
            <td><strong style="color: var(--color-primary);">{{ p.prix | number: '1.2-2' }}</strong></td>
          </tr>
          <tr *ngIf="prices().length === 0">
            <td colspan="4" class="text-center" style="padding: 2rem; color: var(--color-grey-500);">
              Aucun prix enregistré pour cette station
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class PricesComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly api = inject(PricesService);
  private readonly carbApi = inject(CarburantsService);

  stationId = Number(this.route.snapshot.paramMap.get('id'));

  readonly prices = signal<PriceEntry[]>([]);
  readonly carburants = signal<Carburant[]>([]);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  carburantId: number | null = null;
  date: string = new Date().toISOString().slice(0, 10);
  prix: number | null = null;

  constructor() {
    this.fetchCarburants();
    this.fetchPrices();
  }

  fetchCarburants(): void {
    this.carbApi.list(0, 100).subscribe({
      next: (res) => this.carburants.set(res.content),
      error: () => this.error.set('Erreur chargement carburants')
    });
  }

  fetchPrices(): void {
    this.loading.set(true);
    this.api.listByStation(this.stationId).subscribe({
      next: (res) => {
        this.prices.set(res);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Erreur chargement prix');
        this.loading.set(false);
      }
    });
  }

  addPrice(): void {
    if (this.carburantId == null || this.date == null || this.prix == null) return;
    this.loading.set(true);
    this.api.addPrice(this.stationId, {
      carburantId: this.carburantId,
      date: this.date,
      prix: this.prix
    }).subscribe({
      next: () => {
        this.carburantId = null;
        this.prix = null;
        this.fetchPrices();
      },
      error: () => {
        this.error.set('Ajout prix échoué');
        this.loading.set(false);
      }
    });
  }

  carburantName(id: number): string {
    const c = this.carburants().find(x => x.id === id);
    return c ? c.nom : `${id}`;
  }
}


