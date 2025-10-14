# Application E-Commerce JPA
(Voir Architecture de l'application en fichier /ARCHITECTURE.txt)
Application web de gestion e-commerce développée avec Jakarta EE et JPA.

Architecture MVC 2 Respectée

```
┌─────────┐      ┌──────────────┐      ┌──────────┐      ┌──────────┐
│ Client  │─────>│   Servlet    │─────>│   JPA    │─────>│   MySQL  │
│ (JSP)   │<─────│ (Controller) │<─────│ (Model)  │<─────│   (DB)   │
└─────────┘      └──────────────┘      └──────────┘      └──────────┘
```

## Installation et Configuration

### Prérequis
- Java 17+
- Maven 3+
- MySQL 9+
- WildFly ou tout serveur d'application Jakarta EE

### Configuration de la Base de Données

1. Créer la base de données MySQL:
```sql
CREATE DATABASE jpadb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Mettre à jour les informations de connexion dans `src/main/resources/META-INF/persistence.xml`:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/jpadb"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="votre_mot_de_passe"/>
```

### Compilation et Déploiement

1. Compiler le projet:
```bash
./mvnw clean package
```

2. Déployer le fichier WAR généré:
   - Le fichier se trouve dans `target/jpa_app-1.0-SNAPSHOT.war`
   - Copier dans le dossier `deployments` de WildFly
   - Ou déployer via la console d'administration

3. Accéder à l'application:
```
http://localhost:8080/jpa_app-1.0-SNAPSHOT/
```

## Structure du Projet

```
jpa_app/
├── src/main/java/ma/medgm/jpa_app/
│   ├── model/              # Entités JPA
│   │   ├── Produit.java
│   │   ├── Internaute.java
│   │   ├── Panier.java
│   │   ├── LignePanier.java
│   │   └── Vitrine.java
│   ├── Servlet/            # Contrôleurs (Servlets)
│   │   ├── ProduitServlet.java
│   │   ├── InternauteServlet.java
│   │   ├── VitrineServlet.java
│   │   └── PanierServlet.java
│   └── util/
│       └── JpaUtil.java    # Utilitaire JPA
├── src/main/webapp/
│   ├── index.jsp           # Page d'accueil
│   ├── produit/            # Vues Produits
│   ├── internaute/         # Vues Internautes
│   ├── vitrine/            # Vues Vitrines
│   └── panier/             # Vues Panier
└── src/main/resources/META-INF/
    └── persistence.xml     # Configuration JPA
```

## API des Servlets

### ProduitServlet (`/produit`)
- `GET ?action=list` - Liste des produits
- `GET ?action=addForm` - Formulaire d'ajout
- `POST action=add` - Ajouter un produit
- `GET ?action=delete&id=X` - Supprimer un produit

### InternauteServlet (`/internaute`)
- `GET ?action=list` - Liste des internautes
- `GET ?action=addForm` - Formulaire d'ajout
- `POST action=add` - Créer un internaute
- `GET ?action=editForm&id=X` - Formulaire de modification
- `POST action=update` - Modifier un internaute
- `GET ?action=delete&id=X` - Supprimer un internaute

### VitrineServlet (`/vitrine`)
- `GET ?action=list` - Liste des vitrines
- `GET ?action=addForm` - Formulaire de création
- `POST action=add` - Créer une vitrine
- `GET ?action=view&id=X` - Voir détails
- `GET ?action=addProduitForm&vitrineId=X` - Ajouter un produit
- `POST action=addProduit` - Ajouter un produit
- `GET ?action=removeProduit&vitrineId=X&produitId=Y` - Retirer un produit
- `GET ?action=delete&id=X` - Supprimer une vitrine

### PanierServlet (`/panier`)
- `GET ?action=afficher` - Voir le panier
- `GET ?action=ajouter&id=X` - Ajouter un produit
- `GET ?action=supprimer&id=X` - Retirer un produit


## Licence

Projet académique - Usage libre pour l'apprentissage

