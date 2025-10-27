<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Application de Gestion des Etudiants</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .card {
            transition: transform 0.3s;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="text-center mb-5">
            <h1 class="display-4">Application de Gestion des Etudiants</h1>
            <p class="lead">Système de gestion complet avec JEE 10 et EJB</p>
        </div>
        
        <div class="row">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <h5 class="card-title">Gestion des Etudiants</h5>
                        <p class="card-text">Créez, modifiez, listez et supprimez des étudiants</p>
                        <a href="etudiant/list" class="btn btn-primary">Gérer les Etudiants</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <h5 class="card-title">Gestion des Modules</h5>
                        <p class="card-text">Créez, modifiez, listez et supprimez des modules</p>
                        <a href="module/list" class="btn btn-primary">Gérer les Modules</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <h5 class="card-title">Gestion des Notes</h5>
                        <p class="card-text">Créez, modifiez, listez et supprimez des notes</p>
                        <a href="suivie/list" class="btn btn-primary">Gérer les Notes</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

