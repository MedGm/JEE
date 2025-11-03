import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { CarburantsService } from '../../services/carburants.service';
import { CreateCarburantRequest } from '../../models/carburant';

@Component({
  selector: 'app-carburant-form',
  standalone: true,
  imports: [FormsModule, RouterLink, NgIf],
  template: `
    <div class="page-header">
      <h1>{{ isEdit() ? 'Modifier Carburant' : 'Nouveau Carburant' }}</h1>
      <a routerLink="/carburants" class="btn btn-secondary">← Retour</a>
    </div>

    <div class="card">
      <form (ngSubmit)="save()" #f="ngForm">
        <div class="form-group">
          <label>Nom</label>
          <input 
            type="text" 
            name="nom" 
            class="form-control" 
            [(ngModel)]="model.nom" 
            required 
            placeholder="Nom du carburant"
          />
        </div>

        <div class="form-group">
          <label>Description</label>
          <input 
            type="text" 
            name="description" 
            class="form-control" 
            [(ngModel)]="model.description" 
            placeholder="Description (optionnelle)"
          />
        </div>

        <div *ngIf="error()" class="alert alert-error">{{ error() }}</div>

        <div class="flex gap-2" style="margin-top: 1.5rem;">
          <button type="submit" class="btn btn-primary" [disabled]="loading()">
            {{ loading() ? 'Enregistrement...' : (isEdit() ? 'Mettre à jour' : 'Créer') }}
          </button>
          <a routerLink="/carburants" class="btn btn-secondary">Annuler</a>
        </div>
      </form>
    </div>
  `
})
export class CarburantFormComponent {
  private readonly api = inject(CarburantsService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  readonly isEdit = signal(false);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  model: CreateCarburantRequest = { nom: '', description: '' };
  private id: number | null = null;

  constructor() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEdit.set(true);
      this.id = Number(idParam);
      this.loading.set(true);
      this.api.getById(this.id).subscribe({
        next: (c) => {
          this.model = { nom: c.nom, description: c.description };
          this.loading.set(false);
        },
        error: () => {
          this.error.set('Carburant introuvable');
          this.loading.set(false);
        }
      });
    }
  }

  save(): void {
    this.error.set(null);
    this.loading.set(true);
    const req = this.isEdit() && this.id != null
      ? this.api.update(this.id, this.model)
      : this.api.create(this.model);

    req.subscribe({
      next: () => this.router.navigate(['/carburants']),
      error: () => {
        this.error.set('Erreur de sauvegarde');
        this.loading.set(false);
      }
    });
  }
}


