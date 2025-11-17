# Atelier 6 - Application Web MVC Spring

Application web de gestion des absences pour une école maternelle développée avec Spring Boot, Spring MVC, Spring Data JPA et Thymeleaf.

## Description

Cette application permet de gérer les absences des étudiants d'une école maternelle. Elle offre des fonctionnalités complètes de CRUD (Create, Read, Update, Delete) pour les étudiants et leurs absences, avec une interface web moderne et intuitive.

## Technologies utilisées

- **Spring Boot 3.5.7** - Framework Java pour le développement d'applications
- **Spring MVC** - Framework pour la création d'applications web
- **Spring Data JPA** - Abstraction pour l'accès aux données
- **Hibernate** - Framework ORM pour la persistance des données
- **Thymeleaf** - Moteur de template pour les vues
- **MySQL** - Système de gestion de base de données
- **Maven** - Gestionnaire de dépendances et outil de build
- **Jakarta Validation** - Validation des données

## Fonctionnalités

### Gestion des étudiants
- Création, modification et suppression d'étudiants
- Recherche d'étudiants par nom
- Affichage de la liste complète des étudiants
- Validation des données (nom, prénom, email)

### Gestion des absences
- Création, modification et suppression d'absences
- Association d'une absence à un étudiant
- Indication si l'absence est justifiée ou non
- Recherche d'absences par période

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- MySQL 8.0 ou supérieur
- IDE (IntelliJ IDEA recommandé)

## Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/votre-username/atelier6_springmvc.git
cd atelier6_springmvc
```

2. Créer la base de données MySQL :
```sql
CREATE DATABASE ateliermvc CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. Configurer la base de données dans `src/main/resources/application.properties` :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ateliermvc?useSSL=false&serverTimezone=UTC
spring.datasource.username=votre_username
spring.datasource.password=votre_password
```

## Exécution

### Avec Maven Wrapper
```bash
./mvnw spring-boot:run
```

### Avec Maven installé
```bash
mvn spring-boot:run
```

L'application sera accessible à l'adresse : http://localhost:8080

## Structure du projet

```
atelier6_springmvc/
├── src/
│   ├── main/
│   │   ├── java/ma/fstt/atelier6_springmvc/
│   │   │   ├── model/              # Entités JPA
│   │   │   │   ├── Etudiant.java
│   │   │   │   └── Absence.java
│   │   │   ├── repository/         # Couche Repository
│   │   │   │   ├── EtudiantRepository.java
│   │   │   │   └── AbsenceRepository.java
│   │   │   ├── service/            # Couche Service
│   │   │   │   ├── EtudiantService.java
│   │   │   │   ├── EtudiantServiceImpl.java
│   │   │   │   ├── AbsenceService.java
│   │   │   │   └── AbsenceServiceImpl.java
│   │   │   └── web/                # Contrôleurs Spring MVC
│   │   │       ├── HomeController.java
│   │   │       ├── EtudiantController.java
│   │   │       └── AbsenceController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/          # Templates Thymeleaf
│   │           ├── etudiants/
│   │           └── absences/
│   └── test/
└── pom.xml
```

## Architecture

L'application suit l'architecture MVC (Model-View-Controller) :

- **Model** : Entités JPA (Etudiant, Absence)
- **View** : Templates Thymeleaf
- **Controller** : Contrôleurs Spring MVC
- **Service** : Couche de logique métier
- **Repository** : Couche d'accès aux données

## Modèle de données

### Entité Etudiant
- id (Long, clé primaire)
- nom (String, obligatoire)
- prenom (String, obligatoire)
- dateNaissance (LocalDate)
- classe (String)
- email (String, validation email)
- telephone (String)
- createdAt (LocalDateTime)

### Entité Absence
- id (Long, clé primaire)
- etudiant (Etudiant, relation ManyToOne)
- dateAbsence (LocalDate, obligatoire)
- motif (String)
- justifie (boolean)
- remarque (String)
- createdAt (LocalDateTime)

### Relation
Un étudiant peut avoir plusieurs absences (relation One-to-Many).

## URLs de l'application

- Page d'accueil : http://localhost:8080/
- Liste des étudiants : http://localhost:8080/etudiants
- Créer un étudiant : http://localhost:8080/etudiants/create
- Liste des absences : http://localhost:8080/absences
- Créer une absence : http://localhost:8080/absences/create

## Commandes utiles

```bash
# Compiler le projet
./mvnw clean compile

# Exécuter les tests
./mvnw test

# Créer un package JAR
./mvnw clean package

# Exécuter le JAR
java -jar target/atelier6_springmvc-0.0.1-SNAPSHOT.jar
```

## Auteur

**EL GORRIM Mohamed**

Université Abdelmalek Essaadi  
Faculté des Sciences et Techniques de Tanger  
Département Génie Informatique  
Cycle Ingenieur : LSI 2  
Architecture Web Distribuées  
Encadré par : Pr. ELAACHAK LOTFI

## Licence

Ce projet a été développé dans le cadre d'un atelier pédagogique.

