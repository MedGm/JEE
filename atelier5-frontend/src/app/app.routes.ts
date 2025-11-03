import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'stations' },
  {
    path: 'stations',
    loadComponent: () => import('./features/stations/stations.component').then(m => m.StationsComponent)
  },
  {
    path: 'stations/new',
    loadComponent: () => import('./features/stations/station-form.component').then(m => m.StationFormComponent)
  },
  {
    path: 'stations/:id/edit',
    loadComponent: () => import('./features/stations/station-form.component').then(m => m.StationFormComponent)
  },
  {
    path: 'stations/:id/prices',
    loadComponent: () => import('./features/prices/prices.component').then(m => m.PricesComponent)
  },
  {
    path: 'carburants',
    loadComponent: () => import('./features/carburants/carburants.component').then(m => m.CarburantsComponent)
  },
  {
    path: 'carburants/new',
    loadComponent: () => import('./features/carburants/carburant-form.component').then(m => m.CarburantFormComponent)
  },
  {
    path: 'carburants/:id/edit',
    loadComponent: () => import('./features/carburants/carburant-form.component').then(m => m.CarburantFormComponent)
  },
];
