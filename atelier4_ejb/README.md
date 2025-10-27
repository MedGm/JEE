# Application Distribuée JEE - Gestion des Étudiants

**Auteur :** El Gorrim Mohamed  
**Date :** 27/10/2025  
**Outils :** IntelliJ IDEA, Maven, MySQL, JPA 3.0, EJB 4.0, WildFly 37

## 📋 Introduction

Ce projet documente la mise en place d'une application distribuée JEE utilisant les technologies Java Enterprise Edition modernes. L'application permet de gérer des étudiants, des modules et leurs suivis (notes), avec une architecture modulaire, scalable et robuste.

### Technologies Utilisées

- **EJB 4.0** - Couche métier (Enterprise Java Beans)
- **JPA 3.0 (Hibernate)** - Persistance des données
- **WildFly 37** - Serveur d'application JEE
- **MySQL** - Système de gestion de base de données
- **Maven** - Gestion des dépendances et build
- **JSP/Servlets** - Présentation web (MVC 2)

## 🎯 Objectifs

- Créer une application web JEE complète avec pattern MVC 2
- Implémenter un CRUD complet sur 3 entités (Etudiant, Module, Suivie)
- Utiliser des EJB Stateless injectés via JNDI
- Configurer la persistance JPA avec génération automatique du schéma
- Déployer sur WildFly avec DataSource JNDI MySQL

## 📁 Structure du Projet

```
atelier4_ejb/
├── src/main/
│   ├── java/
│   │   ├── entities/          # Entités JPA
│   │   │   ├── Etudiant.java
│   │   │   ├── Module.java
│   │   │   └── Suivie.java
│   │   ├── services/           # EJB Services
│   │   │   ├── EtudiantService.java
│   │   │   ├── EtudiantServiceRemote.java
│   │   │   ├── ModuleService.java
│   │   │   ├── ModuleServiceRemote.java
│   │   │   ├── SuivieService.java
│   │   │   └── SuivieServiceRemote.java
│   │   └── web/               # Servlets MVC 2
│   │       ├── EtudiantAddServlet.java
│   │       ├── EtudiantListServlet.java
│   │       ├── EtudiantEditServlet.java
│   │       ├── EtudiantDeleteServlet.java
│   │       ├── ModuleAddServlet.java
│   │       ├── ModuleListServlet.java
│   │       ├── ModuleEditServlet.java
│   │       ├── ModuleDeleteServlet.java
│   │       ├── SuivieAddServlet.java
│   │       ├── SuivieListServlet.java
│   │       ├── SuivieEditServlet.java
│   │       └── SuivieDeleteServlet.java
│   ├── webapp/
│   │   ├── index.jsp          # Page d'accueil
│   │   ├── etudiant/          # JSP Etudiants
│   │   │   ├── add.jsp
│   │   │   ├── edit.jsp
│   │   │   └── list.jsp
│   │   ├── module/             # JSP Modules
│   │   │   ├── add.jsp
│   │   │   ├── edit.jsp
│   │   │   └── list.jsp
│   │   ├── suivie/             # JSP Notes
│   │   │   ├── add.jsp
│   │   │   ├── edit.jsp
│   │   │   └── list.jsp
│   │   └── WEB-INF/
│   │       └── web.xml
│   └── resources/
│       └── META-INF/
│           └── persistence.xml # Configuration JPA
└── pom.xml                     # Configuration Maven
```

## 🗄️ Schéma de Base de Données

### Base de données : `Getudians`

#### Table : `etudiant`
- `id_etudiant` (INT, Primary Key, Auto Increment)
- `nom` (VARCHAR 50, NOT NULL)
- `prénom` (VARCHAR 50, NOT NULL)
- `cne` (VARCHAR 20, UNIQUE, NOT NULL)
- `adresse` (VARCHAR 100)
- `niveau` (VARCHAR 20)

#### Table : `modules`
- `id_module` (INT, Primary Key, Auto Increment)
- `nom_module` (VARCHAR 100, NOT NULL)

#### Table : `suivie`
- `id_suivie` (INT, Primary Key, Auto Increment)
- `id_etudiant` (INT, Foreign Key → etudiant.id_etudiant)
- `id_module` (INT, Foreign Key → modules.id_module)
- `note` (DOUBLE, NOT NULL)
- `date` (DATE, NOT NULL)

## 🚀 Installation et Configuration

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- WildFly 37
- MySQL 8.0+
- PhpMyAdmin (optionnel)

### 1. Base de Données

```sql
CREATE DATABASE Getudians CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Le schéma sera généré automatiquement par Hibernate grâce à la configuration :
```xml
<property name="hibernate.hbm2ddl.auto" value="create"/>
```

### 2. Configuration WildFly

#### A. Démarrer WildFly
```bash
/opt/wildfly/bin/standalone.sh
```

#### B. Configurer le DataSource JNDI

Accédez à la console d'administration : `http://localhost:9990`

**Configuration via l'interface :**
1. Configuration → Subsystems → Datasources & Drivers → Datasources
2. Ajouter → Datasource
3. Nom : `MySQLDS`
4. JNDI : `java:/MySQLDS`
5. Pilote : MySQL
6. URL : `jdbc:mysql://localhost:3306/Getudians`
7. Utilisateur : `root`
8. Mot de passe : [votre mot de passe]

**OU via CLI :**
```bash
/opt/wildfly/bin/jboss-cli.sh --connect
/subsystem=datasources/data-source=MySQLDS:add(\
  jndi-name=java:/MySQLDS,\
  driver-name=mysql,\
  connection-url=jdbc:mysql://localhost:3306/Getudians,\
  user-name=root,\
  password=your_password\
)
```

### 3. Build et Déploiement

```bash
# Compiler le projet
mvn clean install

# Le WAR sera généré dans target/
# Déployer sur WildFly via l'interface ou en copiant le WAR dans deployments/
```

## 🎨 Fonctionnalités

### CRUD Étudiant
- ✅ **Ajouter** un étudiant (nom, prénom, CNE, adresse, niveau)
- ✅ **Lister** tous les étudiants
- ✅ **Modifier** les informations d'un étudiant
- ✅ **Supprimer** un étudiant

**URLs :**
- `/etudiant/add` - Formulaire d'ajout
- `/etudiant/list` - Liste des étudiants
- `/etudiant/edit?id=X` - Modifier
- `/etudiant/delete?id=X` - Supprimer

### CRUD Module
- ✅ **Ajouter** un module (nom)
- ✅ **Lister** tous les modules
- ✅ **Modifier** un module
- ✅ **Supprimer** un module

**URLs :**
- `/module/add` - Formulaire d'ajout
- `/module/list` - Liste des modules
- `/module/edit?id=X` - Modifier
- `/module/delete?id=X` - Supprimer

### CRUD Suivie (Notes)
- ✅ **Ajouter** une note (étudiant, module, note, date)
- ✅ **Lister** toutes les notes avec détails étudiants/modules
- ✅ **Modifier** une note
- ✅ **Supprimer** une note

**URLs :**
- `/suivie/add` - Formulaire d'ajout
- `/suivie/list` - Liste des notes
- `/suivie/edit?id=X` - Modifier
- `/suivie/delete?id=X` - Supprimer

## 🏗️ Architecture

### Pattern MVC 2

```
┌─────────────────────────────────────────────────┐
│           COUCHE PRÉSENTATION                   │
│  JSP (Vues) ← Servlets (Contrôleurs)            │
└─────────────────────────────────────────────────┘
                    ↓ @EJB
┌─────────────────────────────────────────────────┐
│           COUCHE MÉTIER                         │
│  EJB Stateless (Business Logic)                 │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│           COUCHE PERSISTANCE                    │
│  JPA/Hibernate (ORM)                            │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│           BASE DE DONNÉES                       │
│  MySQL                                          │
└─────────────────────────────────────────────────┘
```

### Injection de Dépendances

- **@EJB** - Injection des services EJB dans les Servlets
- **@PersistenceContext** - Injection de l'EntityManager dans les services
- **@Stateless** - Services métier sans état

## 📝 Fichiers Clés

### persistence.xml
```xml
<persistence-unit name="cnx" transaction-type="JTA">
    <jta-data-source>java:/MySQLDS</jta-data-source>
    <properties>
        <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
        <property name="hibernate.hbm2ddl.auto" value="create"/>
        <property name="hibernate.show_sql" value="true"/>
        <property name="hibernate.format_sql" value="true"/>
    </properties>
</persistence-unit>
```

### pom.xml
- Packaging : WAR
- Java 17
- Dépendances JEE 10, Hibernate, MySQL Connector

## 🔧 Résolution de Problèmes

### Erreurs Communes

1. **LazyInitializationException**
   - Solution : Utiliser `FetchType.EAGER` ou `LEFT JOIN FETCH` dans les requêtes JPA

2. **NotSerializableException**
   - Solution : Implémenter `Serializable` dans les entités

3. **Erreur 404**
   - Solution : Vérifier les mappings `@WebServlet` et les chemins JSP

4. **DataSource non trouvé**
   - Solution : Vérifier la configuration du DataSource dans WildFly

## 📊 Résultats

L'application est maintenant fonctionnelle avec :
- ✅ CRUD complet sur les 3 entités
- ✅ Interface web moderne (Bootstrap)
- ✅ Architecture MVC 2 respectée
- ✅ Injection de dépendances EJB
- ✅ Persistance JPA automatique
- ✅ Déploiement sur WildFly

## 📚 Références

- [EJB3 and JPA Example Tutorial](http://www.javawebtutor.com/articles/ejb/ejb3_and_jpa_example_in_eclipse.php)
- [IBM EJB3 Tutorial](https://www.ibm.com/developerworks/library/os-eclipse-ejb3/index.html)
- [CodesStore EJB3 JPA Tutorial](http://codesstore.blogspot.com/2012/07/ejb3-and-jpa-step-by-step-tutorial.html)
- [How to Create EJB3 JPA Project](http://theopentutorials.com/examples/java-ee/ejb3/how-to-create-ejb3-jpa-project-in-eclipse-jboss-as-5-1/)
- [EJB3 in Eclipse JBoss AS 7.1](https://ibytecode.com/blog/how-to-create-ejb3-jpa-project-in-eclipse-jboss-as-7-1/)
- [Database Module WildFly](http://www.thejavageek.com/2015/01/08/database-module-wildfly/)
