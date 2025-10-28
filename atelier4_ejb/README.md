# Application Distribuée JEE - Gestion des Étudiants

**Auteur :** El Gorrim Mohamed  
**Date :** 27/10/2025  
**Outils :** IntelliJ IDEA, Maven, MySQL, JPA 3.0, EJB 4.0, WildFly 37, Tomcat 11

## 📋 Introduction

Ce projet démontre une **véritable architecture distribuée JEE** avec deux applications séparées communiquant via EJB Remote :

- **Application EJB (atelier4_ejb)** : Déployée sur **WildFly (port 8080)** - Contient la couche métier (Session Beans) et la persistance JPA
- **Application Web (atelier4_ejb_webapp)** : Déployée sur **Tomcat (port 9090)** - Contient la couche présentation (Servlets + JSP)

Les deux applications communiquent via **JNDI** et le **protocole remote+http**, permettant une séparation claire des responsabilités et une scalabilité horizontale.

### Technologies Utilisées

- **EJB 4.0** - Session Beans Stateless avec interfaces Remote
- **JPA 3.0 (Hibernate)** - Persistance des données avec génération automatique du schéma
- **WildFly 37** - Serveur d'application pour la couche métier
- **Tomcat 11** - Serveur web pour la couche présentation
- **MySQL 8.0** - Système de gestion de base de données
- **Maven** - Gestion des dépendances et build
- **JSP/Servlets** - Présentation web (MVC 2)
- **WildFly Naming Client** - Communication JNDI entre serveurs

## 🎯 Objectifs

- ✅ Mettre en place une **architecture distribuée** avec deux serveurs distincts
- ✅ Implémenter la communication **EJB Remote** via JNDI
- ✅ Créer un CRUD complet sur 3 entités (Etudiant, Module, Suivie)
- ✅ Séparer la couche présentation (Tomcat) de la couche métier (WildFly)
- ✅ Configurer la persistance JPA avec génération automatique du schéma
- ✅ Déployer sur WildFly avec DataSource JNDI MySQL
- ✅ Démontrer la scalabilité et flexibilité de l'architecture distribuée

## 📁 Structure du Projet

### Application EJB (atelier4_ejb) - Sur WildFly

```
atelier4_ejb/
├── src/main/
│   ├── java/
│   │   ├── entities/              # Entités JPA
│   │   │   ├── Etudiant.java
│   │   │   ├── Module.java
│   │   │   └── Suivie.java
│   │   └── services/              # EJB Session Beans
│   │       ├── EtudiantService.java        (Stateless)
│   │       ├── EtudiantServiceRemote.java  (Interface Remote)
│   │       ├── ModuleService.java
│   │       ├── ModuleServiceRemote.java
│   │       ├── SuivieService.java
│   │       └── SuivieServiceRemote.java
│   └── resources/
│       └── META-INF/
│           ├── persistence.xml    # Configuration JPA
│           └── beans.xml          # Configuration CDI
└── pom.xml                        # Packaging: ejb
```

### Application Web (atelier4_ejb_webapp) - Sur Tomcat

```
atelier4_ejb_webapp/
├── src/main/
│   ├── java/
│   │   └── web/
│   │       ├── EJBClientUtil.java         # Utilitaire JNDI
│   │       └── remote/                    # Servlets avec appels distants
│   │           ├── EtudiantListServletRemote.java
│   │           ├── EtudiantAddServletRemote.java
│   │           ├── EtudiantEditServletRemote.java
│   │           ├── EtudiantDeleteServletRemote.java
│   │           ├── ModuleListServletRemote.java
│   │           ├── ModuleAddServletRemote.java
│   │           ├── ModuleEditServletRemote.java
│   │           ├── ModuleDeleteServletRemote.java
│   │           ├── SuivieListServletRemote.java
│   │           ├── SuivieAddServletRemote.java
│   │           ├── SuivieEditServletRemote.java
│   │           └── SuivieDeleteServletRemote.java
│   ├── resources/
│   │   └── jndi.properties        # Configuration JNDI
│   └── webapp/
│       ├── index.jsp              # Page d'accueil
│       ├── etudiant/              # JSP Etudiants
│       │   ├── add.jsp
│       │   ├── edit.jsp
│       │   └── list.jsp
│       ├── module/                # JSP Modules
│       │   ├── add.jsp
│       │   ├── edit.jsp
│       │   └── list.jsp
│       ├── suivie/                # JSP Notes
│       │   ├── add.jsp
│       │   ├── edit.jsp
│       │   └── list.jsp
│       └── WEB-INF/
│           ├── web.xml
│           └── beans.xml
└── pom.xml                        # Packaging: war + dépendances WildFly client
```

## 🗄️ Schéma de Base de Données

### Base de données : `Getudians`

#### Table : `etudiant`
- `id_etudiant` (INT, Primary Key, Auto Increment)
- `nom` (VARCHAR 50, NOT NULL)
- `prenom` (VARCHAR 50, NOT NULL)
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
- Tomcat 11
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

#### A. Build du module EJB

```bash
cd atelier4_ejb
mvn clean install

# Génère: target/atelier4_ejb-1.0-SNAPSHOT.jar
```

#### B. Déployer sur WildFly

```bash
# Copier le JAR dans le dossier de déploiement
cp target/atelier4_ejb-1.0-SNAPSHOT.jar /opt/wildfly/standalone/deployments/

# Vérifier les logs WildFly
tail -f /opt/wildfly/standalone/log/server.log

# Chercher les JNDI bindings :
# WFLYEJB0473: JNDI bindings for session bean 'EtudiantService'
# ejb:/atelier4_ejb-1.0-SNAPSHOT/EtudiantService!services.EtudiantServiceRemote
```

#### C. Build de l'application web

```bash
cd ../atelier4_ejb_webapp
mvn clean package

# Génère: target/atelier4_ejb_webapp-1.0-SNAPSHOT.war
```

#### D. Configurer et démarrer Tomcat (port 9090)

Modifier `$TOMCAT_HOME/conf/server.xml` :
```xml
<Connector port="9090" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```

Déployer via IntelliJ IDEA ou copier le WAR dans `$TOMCAT_HOME/webapps/`

#### E. Tester l'application distribuée

- **WildFly :** http://localhost:8080/
- **Application Web :** http://localhost:9090/atelier4_ejb_webapp-1.0-SNAPSHOT/
- **Liste étudiants :** http://localhost:9090/atelier4_ejb_webapp-1.0-SNAPSHOT/etudiant/list

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

## 🏗️ Architecture Distribuée

### Schéma Global

```
┌─────────────────────────────────────────────────────────────────┐
│                    TOMCAT (Port 9090)                           │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  COUCHE PRÉSENTATION                                     │   │
│  │  - JSP (Vues)                                            │   │
│  │  - Servlets (Contrôleurs)                                │   │
│  │  - EJBClientUtil (Lookup JNDI)                           │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                           │
                           │ JNDI Lookup
                           │ remote+http://localhost:8080
                           │
                           ↓
┌─────────────────────────────────────────────────────────────────┐
│                    WILDFLY (Port 8080)                          │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  COUCHE MÉTIER                                           │   │
│  │  - EJB Stateless (@Remote)                               │   │
│  │  - Session Beans                                         │   │
│  └──────────────────────────────────────────────────────────┘   │
│                           ↓                                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  COUCHE PERSISTANCE                                      │   │
│  │  - JPA/Hibernate                                         │   │
│  │  - EntityManager (@PersistenceContext)                   │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                           │
                           ↓
┌─────────────────────────────────────────────────────────────────┐
│                    MySQL (Port 3306)                            │
│                 Base de données Getudians                       │
└─────────────────────────────────────────────────────────────────┘
```

### Communication EJB Remote

**Flux d'une requête :**

1. **Utilisateur** → Accède à `http://localhost:9090/atelier4_ejb_webapp/etudiant/list`
2. **Tomcat/Servlet** → Utilise `EJBClientUtil.lookupEJB()` pour obtenir l'EJB distant
3. **JNDI Lookup** → Connexion à WildFly via `remote+http://localhost:8080`
4. **WildFly/EJB** → Exécute la logique métier (`etudiantService.listerEtudiants()`)
5. **JPA/Hibernate** → Accède à MySQL via DataSource JNDI
6. **Résultat** → Retourné via EJB Remote à Tomcat
7. **JSP** → Affiche les données dans le navigateur

### Classe Utilitaire JNDI

```java
public class EJBClientUtil {
    private static final String WILDFLY_HOST = "localhost";
    private static final int WILDFLY_PORT = 8080;
    
    public static <T> T lookupEJB(String beanName, String interfaceName) {
        String jndiName = "ejb:/atelier4_ejb-1.0-SNAPSHOT//" 
                        + beanName + "!" + interfaceName;
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, 
            "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, 
            "remote+http://" + WILDFLY_HOST + ":" + WILDFLY_PORT);
        Context context = new InitialContext(props);
        return (T) context.lookup(jndiName);
    }
}
```

### Injection et Lookup

**Sur WildFly (EJB) :**
- **@Stateless** - Services métier sans état
- **@Remote** - Interface exposée pour accès distant
- **@PersistenceContext** - Injection de l'EntityManager

**Sur Tomcat (Web) :**
- **JNDI Lookup** - Récupération manuelle des EJBs distants
- **No @EJB injection** - Non supporté sur Tomcat
- **WildFly Client Libraries** - Pour la communication remote

## 📝 Fichiers Clés

### persistence.xml (atelier4_ejb)
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

### jndi.properties (atelier4_ejb_webapp)
```properties
java.naming.factory.initial=org.wildfly.naming.client.WildFlyInitialContextFactory
java.naming.provider.url=remote+http://localhost:8080
jboss.naming.client.ejb.context=true
```

### pom.xml (atelier4_ejb)
- **Packaging :** `ejb`
- **Java 17**
- **Dépendances :** Jakarta EE 10, Hibernate, MySQL Connector, Lombok

### pom.xml (atelier4_ejb_webapp)
- **Packaging :** `war`
- **Java 17**
- **Dépendances :** Jakarta Servlet 6.1, WildFly EJB Client, WildFly Naming Client, Transaction Client
- **Type de dépendance EJB :** `<type>ejb</type>` pour référencer le module EJB

## 🔧 Résolution de Problèmes

### Erreurs Communes

1. **ClassNotFoundException: WildFlyInitialContextFactory**
   - Solution : Ajouter les dépendances WildFly client dans le pom.xml de l'application web
   - Dépendances requises : `jboss-ejb-client`, `wildfly-naming-client`, `wildfly-transaction-client`

2. **NoSuchMethodError avec transactions**
   - Solution : Ajouter `wildfly-transaction-client` pour la compatibilité des transactions distribuées

3. **LazyInitializationException**
   - Solution : Utiliser `FetchType.EAGER` ou `LEFT JOIN FETCH` dans les requêtes JPA

4. **NotSerializableException**
   - Solution : Implémenter `Serializable` dans les entités JPA

5. **Erreur 404 ou mapping de servlet**
   - Solution : Vérifier les annotations `@WebServlet` et les chemins JSP

6. **DataSource non trouvé**
   - Solution : Vérifier la configuration du DataSource dans WildFly

7. **Connection refused to WildFly**
   - Solution : Vérifier que WildFly est démarré sur le port 8080
   - Vérifier le firewall et la configuration de `EJBClientUtil.java`

8. **Duplicate servlet mappings**
   - Solution : Ne pas avoir à la fois les servlets normales et remote - utiliser uniquement remote pour Tomcat

### Logs de Diagnostic

**WildFly :** `/opt/wildfly/standalone/log/server.log`  
**Tomcat :** `$TOMCAT_HOME/logs/catalina.out`

## 📊 Résultats

L'application distribuée est maintenant fonctionnelle avec :

### Architecture
- ✅ **Architecture distribuée** : 2 serveurs (WildFly + Tomcat)
- ✅ **Communication EJB Remote** via JNDI
- ✅ **Séparation des responsabilités** : Présentation / Métier / Persistance
- ✅ **Scalabilité horizontale** possible

### Fonctionnalités
- ✅ **CRUD complet** sur 3 entités (Etudiant, Module, Suivie)
- ✅ **Interface web moderne** avec Bootstrap
- ✅ **Architecture MVC 2** respectée
- ✅ **EJB Session Beans** avec interfaces Remote
- ✅ **Persistance JPA** automatique
- ✅ **Gestion des transactions** distribuées

### Avantages
- 🚀 **Performance** : Séparation web/métier permet le load balancing
- 📈 **Scalabilité** : Plusieurs instances Tomcat peuvent pointer vers WildFly
- 🔧 **Maintenabilité** : Modifications indépendantes des couches
- 🔒 **Sécurité** : Couche métier isolée du frontend
- 🌐 **Réutilisabilité** : Les EJBs peuvent être appelés par d'autres clients

## 🧪 Test de la Communication Distribuée

Une fois les deux serveurs démarrés :

```bash
# Vérifier WildFly
curl http://localhost:8080/

# Vérifier Tomcat
curl http://localhost:9090/atelier4_ejb_webapp-1.0-SNAPSHOT/

# Tester un appel EJB distant
curl http://localhost:9090/atelier4_ejb_webapp-1.0-SNAPSHOT/etudiant/list
```

**Dans les logs WildFly**, vous verrez :
```
INFO [org.jboss.ejb.client] (default task-1) Remote EJB invocation: EtudiantService.listerEtudiants()
```

**Dans les logs Tomcat**, vous verrez :
```
INFO [http-nio-9090-exec-1] WildFly Naming version 1.0.16.Final
INFO [http-nio-9090-exec-1] JBoss EJB Client version 4.0.53.Final
```

Cela confirme la communication **inter-serveurs** réussie !

## 📚 Références

- [EJB3 and JPA Example Tutorial](http://www.javawebtutor.com/articles/ejb/ejb3_and_jpa_example_in_eclipse.php)
- [IBM EJB3 Tutorial](https://www.ibm.com/developerworks/library/os-eclipse-ejb3/index.html)
- [CodesStore EJB3 JPA Tutorial](http://codesstore.blogspot.com/2012/07/ejb3-and-jpa-step-by-step-tutorial.html)
- [How to Create EJB3 JPA Project](http://theopentutorials.com/examples/java-ee/ejb3/how-to-create-ejb3-jpa-project-in-eclipse-jboss-as-5-1/)
- [EJB3 in Eclipse JBoss AS 7.1](https://ibytecode.com/blog/how-to-create-ejb3-jpa-project-in-eclipse-jboss-as-7-1/)
- [Database Module WildFly](http://www.thejavageek.com/2015/01/08/database-module-wildfly/)
- [WildFly EJB Remote Documentation](https://docs.wildfly.org/30/Developer_Guide.html#Remote_EJB_invocations)
