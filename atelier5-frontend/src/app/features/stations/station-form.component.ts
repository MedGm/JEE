import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { StationsService } from '../../services/stations.service';
import { CreateStationRequest, Station } from '../../models/station';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-station-form',
  standalone: true,
  imports: [FormsModule, RouterLink, NgIf],
  template: `
    <div class="page-header">
      <h1>{{ isEdit() ? 'Modifier Station' : 'Nouvelle Station' }}</h1>
      <a routerLink="/stations" class="btn btn-secondary">← Retour</a>
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
            placeholder="Nom de la station"
          />
        </div>

        <div class="form-group">
          <label>Ville</label>
          <input 
            type="text" 
            name="ville" 
            class="form-control" 
            [(ngModel)]="model.ville" 
            required 
            placeholder="Ville"
          />
        </div>

        <div class="form-group">
          <label>Adresse</label>
          <input 
            type="text" 
            name="adresse" 
            class="form-control" 
            [(ngModel)]="model.adresse" 
            required 
            placeholder="Adresse complète"
          />
        </div>

        <div *ngIf="error()" class="alert alert-error">{{ error() }}</div>

        <div class="flex gap-2" style="margin-top: 1.5rem;">
          <button type="submit" class="btn btn-primary" [disabled]="loading()">
            {{ loading() ? 'Enregistrement...' : (isEdit() ? 'Mettre à jour' : 'Créer') }}
          </button>
          <a routerLink="/stations" class="btn btn-secondary">Annuler</a>
        </div>
      </form>
    </div>
  `
})
export class StationFormComponent {
  private readonly api = inject(StationsService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  readonly isEdit = signal(false);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  model: CreateStationRequest = { nom: '', ville: '', adresse: '' };
  private id: number | null = null;

  constructor() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEdit.set(true);
      this.id = Number(idParam);
      this.loading.set(true);
      this.api.getById(this.id).subscribe({
        next: (s) => {
          this.model = { nom: s.nom, ville: s.ville, adresse: s.adresse };
          this.loading.set(false);
        },
        error: () => {
          this.error.set('Station introuvable');
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
      next: () => this.router.navigate(['/stations']),
      error: (err) => {
        this.error.set('Erreur de sauvegarde');
        this.loading.set(false);
      }
    });
  }
}


