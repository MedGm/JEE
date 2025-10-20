# Application E-Commerce JSF & JPA

## Description

Application web e-commerce complète développée avec **JSF 4.0**, **JPA 3.0**, **EclipseLink**, **PrimeFaces** et **MySQL**. Cette application simule le comportement d'un site e-commerce avec gestion des utilisateurs, catalogue produits, panier d'achat et administration.

## Fonctionnalités

### Gestion des Utilisateurs
- Inscription et connexion
- Gestion des profils utilisateurs
- Système de rôles (Admin/Customer)

### Catalogue Produits
- Affichage des produits par catégorie
- Recherche et filtrage
- Détails des produits
- Gestion des stocks

### Panier d'Achat
- Ajout/suppression de produits
- Modification des quantités
- Calcul automatique du total
- Persistance de session

### Gestion des Commandes
- Processus de commande complet
- Suivi des commandes
- Historique des achats

### Administration
- Gestion des produits (CRUD)
- Gestion des utilisateurs
- Suivi des commandes
- Tableaux de bord

## Architecture

```
┌─────────────────────────────────────┐
│        Couche Présentation          │
│      (XHTML + PrimeFaces)           │
├─────────────────────────────────────┤
│        Couche Métier                │
│    (JSF Beans + EntityManager)      │
├─────────────────────────────────────┤
│        Couche Persistance           │
│       (JPA + EclipseLink)           │
├─────────────────────────────────────┤
│         Base de Données             │
│            (MySQL)                  │
└─────────────────────────────────────┘
```

### Architecture Simplifiée

Cette application utilise une architecture simplifiée sans couche DAO :

- **JSF Beans** : Gèrent la logique métier et l'accès aux données via EntityManager
- **EntityManager** : Interface JPA pour les opérations CRUD
- **DataInitializationServlet** : Initialise la base de données avec des données de test
- **Entités JPA** : Modèles de données avec annotations JPA

**Avantages** :
- Code plus simple et direct
- Moins de couches d'abstraction
- Maintenance facilitée
- Performance optimisée

## Technologies

| Technologie | Version | Description |
|-------------|---------|-------------|
| **Java** | 17+ | Langage de programmation |
| **JSF** | 4.0 | Framework web Java |
| **JPA** | 3.0 | API de persistance Java |
| **EclipseLink** | 4.0.7 | Implémentation JPA |
| **PrimeFaces** | 15.0.0 | Bibliothèque de composants UI |
| **CDI** | 5.1.6 | Injection de dépendances |
| **MySQL** | 8.0+ | Base de données relationnelle |
| **WildFly** | 37.0.1 | Serveur d'application |
| **Maven** | 3.6+ | Gestion des dépendances |

## Structure du Projet

```
src/main/java/ma/fstt/atelier3_jsf/
├── entities/           # Entités JPA
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   └── OrderItem.java
├── beans/             # Beans JSF avec EntityManager
│   ├── UserBean.java
│   ├── ProductBean.java
│   ├── CartBean.java
│   └── OrderBean.java
└── util/              # Classes utilitaires
    └── DataInitializationServlet.java

src/main/webapp/
├── index.xhtml        # Page d'accueil
├── login.xhtml        # Connexion
├── register.xhtml     # Inscription
├── products.xhtml     # Catalogue
├── cart.xhtml         # Panier
├── checkout.xhtml     # Commande
├── admin.xhtml        # Administration
├── order-confirmation.xhtml
└── resources/css/
    └── style.css      # Styles personnalisés
```

## Installation et Configuration

### Prérequis
- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- MySQL 8.0 ou supérieur
- WildFly 37.x

### 1. Configuration de la Base de Données

Créer la base de données MySQL :
```sql
CREATE DATABASE ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configuration de l'Application

Modifier les credentials dans `src/main/resources/META-INF/persistence.xml` :
```xml
<property name="jakarta.persistence.jdbc.user" value="votre_username"/>
<property name="jakarta.persistence.jdbc.password" value="votre_password"/>
```

### 3. Compilation et Déploiement

```bash
# Compiler le projet
mvn clean package

# Déployer sur WildFly
# Copier target/atelier3_jsf-1.0-SNAPSHOT.war vers WILDFLY_HOME/standalone/deployments/
```

### 4. Accès à l'Application

URL : `http://localhost:8080/atelier3_jsf-1.0-SNAPSHOT/`

## Comptes par Défaut

| Rôle | Email | Mot de passe |
|------|-------|--------------|
| **Admin** | medgm@store.com | medgm123 |
| **Customer** | customer@store.com | customer123 |

## Résolution des Problèmes Techniques

### Compatibilité PrimeFaces avec WildFly 37

**Problème** : Incompatibilité des namespaces `javax.*` vs `jakarta.*`

**Solution** : Utilisation de PrimeFaces 15.0.0 avec classifier Jakarta
```xml
<dependency>
    <groupId>org.primefaces</groupId>
    <artifactId>primefaces</artifactId>
    <version>15.0.0</version>
    <classifier>jakarta</classifier>
</dependency>
```

### Configuration Base de Données

**Problème** : EclipseLink se connectait à H2 au lieu de MySQL

**Solution** : Configuration explicite dans `persistence.xml`
```xml
<persistence-unit name="default" transaction-type="RESOURCE_LOCAL">
    <property name="eclipselink.target-database" value="MySQL"/>
    <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
    <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/ecommerce_db"/>
</persistence-unit>
```

## Schéma de Base de Données

L'application génère automatiquement les tables suivantes :

- **users** - Comptes utilisateurs
- **categories** - Catégories de produits
- **products** - Catalogue des produits
- **carts** - Paniers d'achat
- **cart_items** - Articles dans les paniers
- **orders** - Commandes clients
- **order_items** - Articles dans les commandes

## Fonctionnalités Détaillées

### Pour les Clients
1. **Navigation** : Parcourir les produits par catégorie
2. **Recherche** : Rechercher des produits par nom
3. **Panier** : Ajouter/modifier/supprimer des articles
4. **Commande** : Finaliser l'achat avec adresse de livraison
5. **Compte** : Gérer le profil utilisateur

### Pour les Administrateurs
1. **Produits** : CRUD complet des produits
2. **Utilisateurs** : Gestion des comptes clients
3. **Commandes** : Suivi et gestion des commandes
4. **Statistiques** : Vue d'ensemble des ventes

## Patterns Utilisés

- **MVC Pattern** : Séparation des responsabilités
- **CDI Pattern** : Injection de dépendances
- **EntityManager Pattern** : Accès direct aux données via JPA
- **Servlet Pattern** : Initialisation de la base de données

## Licence

Ce projet est développé à des fins éducatives dans le cadre du cours JEE à la FSTT.
---

**Note** : Cette application démontre la maîtrise des technologies Java Enterprise Edition (JEE) avec un focus particulier sur JSF 4.0 et JPA 3.0.