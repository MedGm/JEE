import { Component, inject, signal } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CarburantsService } from '../../services/carburants.service';
import { Carburant } from '../../models/carburant';

@Component({
  selector: 'app-carburants',
  standalone: true,
  imports: [NgIf, NgFor, RouterLink],
  template: `
    <div class="page-header">
      <h1>Carburants</h1>
      <a routerLink="/carburants/new" class="btn btn-primary">
        <span>+</span>
        <span>Nouveau carburant</span>
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
            <th>Description</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let c of carburants()">
            <td><span class="badge badge-primary">{{ c.id }}</span></td>
            <td><strong>{{ c.nom }}</strong></td>
            <td>{{ c.description || '-' }}</td>
            <td>
              <div class="flex gap-1" style="flex-wrap: wrap;">
                <a [routerLink]="['/carburants', c.id, 'edit']" class="btn btn-sm btn-secondary">Modifier</a>
                <button (click)="remove(c)" class="btn btn-sm btn-danger">Supprimer</button>
              </div>
            </td>
          </tr>
          <tr *ngIf="carburants().length === 0">
            <td colspan="4" class="text-center" style="padding: 2rem; color: var(--color-grey-500);">
              Aucun carburant trouvé
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
export class CarburantsComponent {
  private readonly api = inject(CarburantsService);

  readonly carburants = signal<Carburant[]>([]);
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
          this.carburants.set(res.content);
          this.totalPages.set(res.totalPages || 1);
          this.loading.set(false);
        },
        error: () => {
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

  remove(c: Carburant): void {
    if (!confirm(`Supprimer le carburant "${c.nom}" ?`)) return;
    this.api.delete(c.id).subscribe({
      next: () => this.fetch(),
      error: () => this.error.set('Suppression échouée')
    });
  }
}


