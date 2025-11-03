import { Component, computed, effect, inject, signal } from '@angular/core';
import { NgIf, NgFor, DecimalPipe, AsyncPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { StationsService } from '../../services/stations.service';
import { Station } from '../../models/station';

@Component({
  selector: 'app-stations',
  standalone: true,
  imports: [NgIf, NgFor, RouterLink, DecimalPipe, AsyncPipe, DatePipe],
  template: `
    <div class="page-header">
      <h1>Stations</h1>
      <a routerLink="/stations/new" class="btn btn-primary">
        <span>+</span>
        <span>Nouvelle station</span>
      </a>
    </div>

    <div *ngIf="loading()" class="loading">Chargement...</div>
    <div *ngIf="error()" class="alert alert-error">{{ error() }}</div>

    <div *ngIf="!loading() && !error()" class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Ville</th>
            <th>Adresse</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let s of stations()">
            <td><span class="badge badge-primary">{{ s.id }}</span></td>
            <td><strong>{{ s.nom }}</strong></td>
            <td>{{ s.ville }}</td>
            <td>{{ s.adresse }}</td>
            <td>
              <div class="flex gap-1" style="flex-wrap: wrap;">
                <a [routerLink]="['/stations', s.id, 'edit']" class="btn btn-sm btn-secondary">Modifier</a>
                <a [routerLink]="['/stations', s.id, 'prices']" class="btn btn-sm btn-primary">Prix</a>
                <button (click)="remove(s)" class="btn btn-sm btn-danger">Supprimer</button>
              </div>
            </td>
          </tr>
          <tr *ngIf="stations().length === 0">
            <td colspan="5" class="text-center" style="padding: 2rem; color: var(--color-grey-500);">
              Aucune station trouvée
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div *ngIf="!loading()" class="pagination">
      <button class="btn btn-secondary" (click)="prev()" [disabled]="page() === 0">Précédent</button>
      <span class="pagination-info">Page {{ page() + 1 }} / {{ totalPages() }}</span>
      <button class="btn btn-secondary" (click)="next()" [disabled]="page() + 1 >= totalPages()">Suivant</button>
    </div>
  `
})
export class StationsComponent {
  private readonly api = inject(StationsService);

  readonly stations = signal<Station[]>([]);
  readonly page = signal(0);
  readonly size = signal(10);
  readonly totalPages = signal(1);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  constructor() {
    this.fetch();
  }

  fetch(): void {
    this.loading.set(true);
    this.error.set(null);
    this.api.list(this.page(), this.size())
      .subscribe({
        next: (res) => {
          this.stations.set(res.content);
          this.totalPages.set(res.totalPages || 1);
          this.loading.set(false);
        },
        error: (err) => {
          this.error.set('Erreur de chargement');
          this.loading.set(false);
        }
      });
  }

  next(): void {
    if (this.page() + 1 < this.totalPages()) {
      this.page.update(v => v + 1);
      this.fetch();
    }
  }

  prev(): void {
    if (this.page() > 0) {
      this.page.update(v => v - 1);
      this.fetch();
    }
  }

  remove(s: Station): void {
    if (!confirm(`Supprimer la station "${s.nom}" ?`)) return;
    this.api.delete(s.id).subscribe({
      next: () => this.fetch(),
      error: () => this.error.set('Suppression échouée')
    });
  }
}


