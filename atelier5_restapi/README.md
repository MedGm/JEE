# Atelier 5 - Web Services REST API

**Gestion des Stations et Prix des Carburants**

---

## Informations Académiques

**Université Abdelmalek Essaadi**  
**Faculté des Sciences et Techniques de Tanger**  
**Département Génie Informatique**

**Cycle:** Ingénieur LSI  
**Module:** Applications Distribuées  
**Professeur:** Pr. ELAACHAK LOTFI  
**Année:** 2025

---

## Objectif du Projet

L'objectif principal de cet atelier est de pratiquer la mise en place d'une variété de web services basés sur plusieurs architectures. Durant cet atelier, l'étudiant est amené à implémenter avec Spring Boot un web service dont le rôle principal est de garantir la bonne gestion des stations et prix des carburants.

## Partie 1 : Mise en place des Web Services (Backend)

Cette partie consiste à :

1. **Traduire le diagramme de classes en graphe d'objets** dans la couche persistance en se basant sur l'API JPA
2. **Implémenter les méthodes service** qui vont gérer la partie backend :
   - CRUD Station
   - CRUD Carburant
   - Gestion des prix journaliers du carburant d'une station donnée
3. **Implémenter les méthodes pour un web service** de type RestController
4. **Tester les différents web services** en utilisant Postman

---

## Technologies Utilisées

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Lombok**
- **Maven**
- **Tomcat** (embedded)

## Outils de Développement

- **IntelliJ IDEA**
- **Maven**
- **Postman** (pour les tests)

---

## Architecture du Projet

```
src/main/java/ma/fstt/atelier5_restapi/
├── controller/          # REST Controllers (Couche présentation)
├── service/            # Interfaces de services (Couche métier)
├── service/impl/       # Implémentations des services
├── repository/         # Repositories JPA (Couche persistance)
├── domain/             # Entités JPA (Modèle de données)
├── dto/                # Data Transfer Objects
├── dto/request/        # DTOs pour les requêtes
├── mapper/             # Mappers Entity ↔ DTO
└── exception/          # Gestion des exceptions
```

---

## Modèle de Données

### Diagramme de Classes

Le système gère trois entités principales :

#### 1. Station
Représente une station-service avec les attributs suivants :
- `id` (Long) : Identifiant unique
- `nom` (String) : Nom de la station
- `ville` (String) : Ville où se trouve la station
- `adresse` (String) : Adresse complète de la station
- `histo` (List<HistoCarb>) : Liste des historiques de prix (relation OneToMany)

#### 2. Carburant
Représente un type de carburant (ex: Diesel, SP95) avec :
- `id` (Long) : Identifiant unique
- `nom` (String) : Nom du carburant
- `description` (String) : Description du carburant

#### 3. HistoCarb
Représente l'historique des prix journaliers liant une Station et un Carburant :
- `id` (Long) : Identifiant unique
- `date` (LocalDate) : Date du prix
- `prix` (BigDecimal) : Prix du carburant
- `station` (Station) : Relation ManyToOne vers Station
- `carburant` (Carburant) : Relation ManyToOne vers Carburant

### Relations JPA

- **HistoCarb** → **Station** : `@ManyToOne` (fetch = LAZY)
- **HistoCarb** → **Carburant** : `@ManyToOne` (fetch = LAZY)
- **Station** → **HistoCarb** : `@OneToMany` (cascade = ALL, orphanRemoval = true)

---

## Configuration et Exécution

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- IDE IntelliJ IDEA (recommandé)

### Installation et Exécution

```bash
# Cloner le projet (si applicable)
git clone <repository-url>
cd atelier5_restapi

# Compiler le projet
mvn clean install

# Exécuter l'application
./mvnw spring-boot:run

# Ou avec Maven directement
mvn spring-boot:run
```

L'application démarre sur : `http://localhost:8080`

### Console H2

Pour accéder à la console de la base de données H2 :

1. Ouvrir un navigateur
2. Aller à : `http://localhost:8080/h2-console`
3. Remplir les informations de connexion :
   - **JDBC URL:** `jdbc:h2:mem:atelier`
   - **Username:** `sa`
   - **Password:** (laisser vide)

**Note importante:** La base de données est en mémoire et se réinitialise à chaque redémarrage de l'application.

---

## API REST - Documentation Complète

Base URL : `http://localhost:8080/api`

---

## API Stations

### 1. Lister toutes les stations (avec pagination)

**GET** `/api/stations`

**Paramètres de requête (optionnels) :**
- `page` : Numéro de page (par défaut: 0)
- `size` : Taille de la page (par défaut: 20)
- `sort` : Tri (ex: `sort=nom,asc`)

**Exemple de requête :**
```http
GET http://localhost:8080/api/stations?page=0&size=20
```

**Réponse :** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "nom": "Station A",
      "ville": "Rabat",
      "adresse": "123 Avenue"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

### 2. Récupérer une station par ID

**GET** `/api/stations/{id}`

**Paramètres de chemin :**
- `id` : Identifiant de la station

**Exemple de requête :**
```http
GET http://localhost:8080/api/stations/1
```

**Réponse :** `200 OK`
```json
{
  "id": 1,
  "nom": "Station A",
  "ville": "Rabat",
  "adresse": "123 Avenue"
}
```

**Erreur :** `404 Not Found` si la station n'existe pas

---

### 3. Créer une nouvelle station

**POST** `/api/stations`

**Corps de la requête :**
```json
{
  "nom": "Station A",
  "ville": "Rabat",
  "adresse": "123 Avenue"
}
```

**Validation :**
- `nom` : Obligatoire (ne doit pas être vide)
- `ville` : Obligatoire (ne doit pas être vide)
- `adresse` : Obligatoire (ne doit pas être vide)

**Exemple de requête :**
```http
POST http://localhost:8080/api/stations
Content-Type: application/json

{
  "nom": "Station A",
  "ville": "Rabat",
  "adresse": "123 Avenue"
}
```

**Réponse :** `201 Created`
```json
{
  "id": 1,
  "nom": "Station A",
  "ville": "Rabat",
  "adresse": "123 Avenue"
}
```

---

### 4. Mettre à jour une station

**PUT** `/api/stations/{id}`

**Paramètres de chemin :**
- `id` : Identifiant de la station

**Corps de la requête :**
```json
{
  "nom": "Station A+",
  "ville": "Rabat",
  "adresse": "123 Avenue, Quartier X"
}
```

**Exemple de requête :**
```http
PUT http://localhost:8080/api/stations/1
Content-Type: application/json

{
  "nom": "Station A+",
  "ville": "Rabat",
  "adresse": "123 Avenue, Quartier X"
}
```

**Réponse :** `200 OK`
```json
{
  "id": 1,
  "nom": "Station A+",
  "ville": "Rabat",
  "adresse": "123 Avenue, Quartier X"
}
```

**Erreur :** `404 Not Found` si la station n'existe pas

---

### 5. Supprimer une station

**DELETE** `/api/stations/{id}`

**Paramètres de chemin :**
- `id` : Identifiant de la station

**Exemple de requête :**
```http
DELETE http://localhost:8080/api/stations/1
```

**Réponse :** `204 No Content`

**Erreur :** `404 Not Found` si la station n'existe pas

---

## API Historique des Prix

### 6. Ajouter un prix à une station

**POST** `/api/stations/{stationId}/prices`

**Paramètres de chemin :**
- `stationId` : Identifiant de la station

**Corps de la requête :**
```json
{
  "carburantId": 1,
  "date": "2025-10-01",
  "prix": 12.34
}
```

**Validation :**
- `carburantId` : Obligatoire
- `date` : Obligatoire, doit être une date passée ou présente
- `prix` : Obligatoire, doit être >= 0.0

**Exemple de requête :**
```http
POST http://localhost:8080/api/stations/1/prices
Content-Type: application/json

{
  "carburantId": 1,
  "date": "2025-10-01",
  "prix": 12.34
}
```

**Réponse :** `201 Created`
```json
{
  "id": 1,
  "date": "2025-10-01",
  "prix": 12.34,
  "stationId": 1,
  "carburantId": 1
}
```

**Erreurs possibles :**
- `404 Not Found` si la station ou le carburant n'existe pas
- `400 Bad Request` en cas d'erreurs de validation

---

### 7. Récupérer les prix d'une station

**GET** `/api/stations/{stationId}/prices`

**Paramètres de chemin :**
- `stationId` : Identifiant de la station

**Paramètres de requête (optionnels) :**
- `from` : Date de début (format: `YYYY-MM-DD`)
- `to` : Date de fin (format: `YYYY-MM-DD`)

**Exemples de requêtes :**
```http
# Récupérer tous les prix d'une station
GET http://localhost:8080/api/stations/1/prices

# Récupérer les prix dans une plage de dates
GET http://localhost:8080/api/stations/1/prices?from=2025-09-01&to=2025-10-15
```

**Réponse :** `200 OK`
```json
[
  {
    "id": 1,
    "date": "2025-10-01",
    "prix": 12.34,
    "stationId": 1,
    "carburantId": 1
  },
  {
    "id": 2,
    "date": "2025-10-02",
    "prix": 12.50,
    "stationId": 1,
    "carburantId": 1
  }
]
```

---

## API Carburants

### 8. Lister tous les carburants (avec pagination)

**GET** `/api/carburants`

**Paramètres de requête (optionnels) :**
- `page` : Numéro de page (par défaut: 0)
- `size` : Taille de la page (par défaut: 20)
- `sort` : Tri

**Exemple de requête :**
```http
GET http://localhost:8080/api/carburants?page=0&size=20
```

**Réponse :** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "nom": "Diesel",
      "description": "Gasoil"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

### 9. Créer un nouveau carburant

**POST** `/api/carburants`

**Corps de la requête :**
```json
{
  "nom": "Diesel",
  "description": "Gasoil"
}
```

**Validation :**
- `nom` : Obligatoire (ne doit pas être vide)
- `description` : Optionnel

**Exemple de requête :**
```http
POST http://localhost:8080/api/carburants
Content-Type: application/json

{
  "nom": "Diesel",
  "description": "Gasoil"
}
```

**Réponse :** `201 Created`
```json
{
  "id": 1,
  "nom": "Diesel",
  "description": "Gasoil"
}
```

---

### 10. Mettre à jour un carburant

**PUT** `/api/carburants/{id}`

**Paramètres de chemin :**
- `id` : Identifiant du carburant

**Corps de la requête :**
```json
{
  "nom": "Diesel Premium",
  "description": "Cetane 55"
}
```

**Exemple de requête :**
```http
PUT http://localhost:8080/api/carburants/1
Content-Type: application/json

{
  "nom": "Diesel Premium",
  "description": "Cetane 55"
}
```

**Réponse :** `200 OK`
```json
{
  "id": 1,
  "nom": "Diesel Premium",
  "description": "Cetane 55"
}
```

**Erreur :** `404 Not Found` si le carburant n'existe pas

---

### 11. Supprimer un carburant

**DELETE** `/api/carburants/{id}`

**Paramètres de chemin :**
- `id` : Identifiant du carburant

**Exemple de requête :**
```http
DELETE http://localhost:8080/api/carburants/1
```

**Réponse :** `204 No Content`

**Erreur :** `404 Not Found` si le carburant n'existe pas

---

## Gestion des Erreurs

L'API retourne des codes de statut HTTP standard avec des détails d'erreur :

### 404 Not Found
```json
{
  "timestamp": "2025-11-03T20:11:13.332",
  "status": 404,
  "error": "Not Found",
  "message": "Station with id 1 not found"
}
```

### 400 Bad Request (Erreur de validation)
```json
{
  "timestamp": "2025-11-03T20:11:13.332",
  "status": 400,
  "error": "Validation Failed",
  "message": "Request validation failed",
  "errors": {
    "nom": "must not be blank",
    "prix": "must be greater than or equal to 0.0"
  }
}
```

### 400 Bad Request (Argument invalide)
```json
{
  "timestamp": "2025-11-03T20:11:13.332",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2025-11-03T20:11:13.332",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Unexpected error occurred"
}
```

---

## Tests avec Postman

### Ordre de test recommandé

Comme la base de données est en mémoire et démarre vide, suivez cet ordre :

1. **Créer un Carburant**
   - POST `/api/carburants`
   - Sauvegarder l'`id` retourné

2. **Créer une Station**
   - POST `/api/stations`
   - Sauvegarder l'`id` retourné

3. **Ajouter un Prix**
   - POST `/api/stations/{stationId}/prices`
   - Utiliser les `carburantId` et `stationId` des étapes 1 et 2

### Configuration Postman

1. Créer une nouvelle Collection : "Atelier5 REST API"
2. Créer un Environment avec les variables :
   - `baseUrl`: `http://localhost:8080`
   - `carburantId`: (sera défini après création)
   - `stationId`: (sera défini après création)
3. Utiliser les variables dans les requêtes : `{{baseUrl}}/api/stations/{{stationId}}/prices`

---

## Format des Dates

Toutes les dates doivent être au format ISO 8601 : `YYYY-MM-DD`

Exemples :
- `2025-10-01`
- `2025-12-31`

---

## En-têtes de Requête

Pour les requêtes POST et PUT, inclure :
```
Content-Type: application/json
```

Pour toutes les requêtes, optionnel :
```
Accept: application/json
```

---

## Fonctionnalités Implémentées

**CRUD complet pour Stations**
- Création, lecture, mise à jour, suppression
- Pagination et tri

**CRUD complet pour Carburants**
- Création, lecture, mise à jour, suppression
- Pagination et tri

**Gestion des prix journaliers**
- Ajout de prix pour une station et un carburant
- Récupération des prix avec filtrage par date

**Validation des données**
- Validation des champs requis
- Validation des types de données
- Messages d'erreur détaillés

**Gestion des exceptions**
- Exceptions personnalisées
- Codes HTTP appropriés (404, 400, 500)
- Messages d'erreur clairs

**Architecture en couches**
- Séparation des responsabilités
- DTOs pour l'isolation des entités
- Mappers pour la conversion Entity ↔ DTO

---

## Notes Importantes

- La base de données est **en mémoire** (H2) et se réinitialise à chaque redémarrage
- Tous les endpoints supportent la pagination où applicable
- La validation est effectuée sur tous les corps de requête
- L'API suit les conventions RESTful
- Les réponses d'erreur incluent des messages détaillés pour le débogage

---

## Partie 2 : Frontend (À venir)

La partie 2 consiste à développer la partie frontend avec Angular qui reflète le métier implémenté dans la partie backend.

---

## Auteur

**Étudiant** - LSI  
**Université Abdelmalek Essaadi**  
**Faculté des Sciences et Techniques de Tanger**

---

## Remerciements

- **Pr. ELAACHAK LOTFI** pour l'encadrement et les cours
- **Université Abdelmalek Essaadi** pour l'opportunité d'apprentissage

---

## Licence

Ce projet est réalisé dans le cadre d'un atelier académique à des fins éducatives.

---

**Date de réalisation :** 2025
