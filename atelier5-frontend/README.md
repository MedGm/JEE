# Atelier 5 - Gestion des Stations et Prix de Carburants

## Informations du Projet

**Université :** Abdelmalek Essaadi  
**Faculté :** Sciences et Techniques de Tanger  
**Département :** Génie Informatique  
**Cycle :** Ingénieur LSI  
**Matière :** Applications Distribuées  
**Enseignant :** Pr. ELAACHAK LOTFI  

---

## Objectif

L'objectif principal de cet atelier est de pratiquer la mise en place d'une variété de web services basés sur plusieurs architectures. Durant cet atelier, l'étudiant sera amené à implémenter un web service avec Spring Boot dont le rôle principal est de garantir la bonne gestion des stations et prix des carburants.

---

## Architecture du Projet

Ce projet est composé de deux parties principales :

### Partie 1 : Backend (REST API)
- **Technologies :** Spring Boot, JPA, H2 Database
- **Repository :** `atelier5_restapi` (séparé)
- **Responsabilités :** CRUD Stations, CRUD Carburants, Gestion des prix journaliers

### Partie 2 : Frontend (Angular)
- **Technologies :** Angular 20, TypeScript, RxJS
- **Repository :** `atelier5-frontend` (ce projet)
- **Responsabilités :** Interface utilisateur pour interagir avec l'API REST

---

## Technologies Utilisées

### Frontend
- **Angular** 20.3.0
- **TypeScript** 5.9.2
- **RxJS** 7.8.0
- **Angular Router** - Navigation et routing
- **Angular Forms** - Gestion des formulaires
- **Angular HTTP Client** - Communication avec l'API

### Outils de Développement
- **Node.js** & **npm**
- **Angular CLI** 20.3.8
- **VSCode** / **IntelliJ IDEA**

---

## Structure du Projet

```
atelier5-frontend/
├── src/
│   ├── app/
│   │   ├── features/
│   │   │   ├── stations/          # Composants pour les stations
│   │   │   │   ├── stations.component.ts
│   │   │   │   └── station-form.component.ts
│   │   │   ├── carburants/        # Composants pour les carburants
│   │   │   │   ├── carburants.component.ts
│   │   │   │   └── carburant-form.component.ts
│   │   │   └── prices/            # Composants pour les prix
│   │   │       └── prices.component.ts
│   │   ├── models/                # Interfaces TypeScript
│   │   │   ├── station.ts
│   │   │   ├── carburant.ts
│   │   │   └── price.ts
│   │   ├── services/             # Services HTTP
│   │   │   ├── stations.service.ts
│   │   │   ├── carburants.service.ts
│   │   │   └── prices.service.ts
│   │   ├── app.ts                # Composant racine
│   │   ├── app.routes.ts         # Configuration des routes
│   │   └── app.config.ts         # Configuration de l'application
│   ├── styles.css                # Styles globaux
│   └── index.html
├── angular.json                  # Configuration Angular
├── package.json                  # Dépendances npm
├── proxy.conf.json              # Configuration proxy pour CORS
└── README.md                    # Ce fichier
```

---

## Installation et Démarrage

### Prérequis

- **Node.js** (version 18 ou supérieure)
- **npm** (généralement inclus avec Node.js)
- Backend Spring Boot en cours d'exécution sur `http://localhost:8080`

### Étapes d'Installation

1. **Cloner le repository** (si applicable)
   ```bash
   git clone <repository-url>
   cd atelier5-frontend
   ```

2. **Installer les dépendances**
   ```bash
   npm install
   ```

3. **Démarrer le serveur de développement**
   ```bash
   npm start
   # ou
   ng serve
   ```

4. **Accéder à l'application**
   
   Ouvrez votre navigateur et accédez à : `http://localhost:4200`

### Configuration du Proxy

Le projet utilise un proxy pour éviter les problèmes CORS lors du développement. La configuration est définie dans `proxy.conf.json` :

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```

---

## Fonctionnalités

### 1. Gestion des Stations 

- **Liste des stations** avec pagination
- **Création** d'une nouvelle station
- **Modification** d'une station existante
- **Suppression** d'une station
- **Consultation** de l'historique des prix d'une station

### 2. Gestion des Carburants 

- **Liste des carburants** avec pagination
- **Création** d'un nouveau carburant
- **Modification** d'un carburant existant
- **Suppression** d'un carburant

### 3. Gestion des Prix 

- **Ajout** d'un nouveau prix pour une station et un carburant
- **Consultation** de l'historique des prix par station
- **Filtrage** par date (optionnel)

---

## Design UI/UX

L'interface utilise un design moderne avec une palette de couleurs cohérente :

- **Couleur principale :** Vert (`#2d8659`)
- **Fond :** Gris clair (`#f9fafb`)
- **Cartes :** Blanc avec ombres subtiles
- **Typographie :** Système de polices modernes

### Caractéristiques du Design

- Interface responsive (mobile-friendly)
- Navigation intuitive avec menu en haut
- Formulaires élégants avec validation
- Tableaux avec effets hover
- Badges et indicateurs visuels
- Messages d'erreur et de chargement
- Pagination claire

---

## API Endpoints

L'application communique avec le backend via les endpoints suivants :

### Stations
- `GET /api/stations` - Liste paginée des stations
- `GET /api/stations/{id}` - Détails d'une station
- `POST /api/stations` - Créer une station
- `PUT /api/stations/{id}` - Modifier une station
- `DELETE /api/stations/{id}` - Supprimer une station

### Carburants
- `GET /api/carburants` - Liste paginée des carburants
- `GET /api/carburants/{id}` - Détails d'un carburant
- `POST /api/carburants` - Créer un carburant
- `PUT /api/carburants/{id}` - Modifier un carburant
- `DELETE /api/carburants/{id}` - Supprimer un carburant

### Prix
- `POST /api/stations/{stationId}/prices` - Ajouter un prix
- `GET /api/stations/{stationId}/prices` - Historique des prix d'une station

---

## Build de Production

Pour créer une version de production :

```bash
ng build
```

Les fichiers de production seront générés dans le dossier `dist/`.

Pour une build optimisée :

```bash
ng build --configuration production
```

---

## Tests

### Tests Unitaires

```bash
ng test
```

### Tests E2E

```bash
ng e2e
```

---

## Modèles de Données

### Station
```typescript
interface Station {
  id: number;
  nom: string;
  ville: string;
  adresse: string;
}
```

### Carburant
```typescript
interface Carburant {
  id: number;
  nom: string;
  description?: string;
}
```

### PriceEntry
```typescript
interface PriceEntry {
  id: number;
  date: string; // YYYY-MM-DD
  prix: number;
  stationId: number;
  carburantId: number;
}
```

---

## Workflow de Développement

1. **Créer un carburant** (nécessaire avant d'ajouter des prix)
2. **Créer une station**
3. **Ajouter des prix** pour la station avec les carburants disponibles
4. **Consulter l'historique** des prix

---

## Résolution de Problèmes

### Erreur CORS

Si vous rencontrez des erreurs CORS, assurez-vous que :
- Le backend est en cours d'exécution sur `http://localhost:8080`
- Le proxy est configuré dans `angular.json`
- Vous avez redémarré le serveur de développement après la configuration du proxy

### Erreur de Connexion

Vérifiez que :
- Le backend Spring Boot est démarré
- L'URL de l'API dans les services correspond à votre configuration backend

---

## Ressources

- [Documentation Angular](https://angular.dev)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [REST API Best Practices](https://restfulapi.net/)

---

## Auteur

**Étudiant :** ELGORRIM MOHAMED
**Année :** 2025-2026  
**Université Abdelmalek Essaadi - FST Tanger**

---

## Licence

Ce projet est réalisé dans le cadre d'un atelier pédagogique à l'Université Abdelmalek Essaadi.

---

## Notes

Ce projet fait partie de l'atelier 5 sur les applications distribuées. Il démontre l'implémentation d'une architecture REST avec Spring Boot pour le backend et Angular pour le frontend, permettant la gestion complète d'un système de stations-service et de leurs prix de carburants.

