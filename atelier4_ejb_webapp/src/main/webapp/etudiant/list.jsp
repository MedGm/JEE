<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Etudiant" %>
<html>
<head>
    <title>Liste des Etudiants</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Liste des Etudiants</h2>
        <a href="add" class="btn btn-success mb-3">Ajouter un Etudiant</a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>CNE</th>
                    <th>Adresse</th>
                    <th>Niveau</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
                    if (etudiants != null) {
                        for (Etudiant e : etudiants) {
                %>
                <tr>
                    <td><%= e.getIdEtudiant() %></td>
                    <td><%= e.getNom() %></td>
                    <td><%= e.getPrenom() %></td>
                    <td><%= e.getCne() %></td>
                    <td><%= e.getAdresse() != null ? e.getAdresse() : "" %></td>
                    <td><%= e.getNiveau() != null ? e.getNiveau() : "" %></td>
                    <td>
                        <a href="edit?id=<%= e.getIdEtudiant() %>" class="btn btn-sm btn-warning">Modifier</a>
                        <a href="delete?id=<%= e.getIdEtudiant() %>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant?')">Supprimer</a>
                    </td>
                </tr>
                <% 
                        }
                    }
                %>
            </tbody>
        </table>
        <a href="../index.jsp" class="btn btn-secondary">Retour à l'accueil</a>
    </div>
</body>
</html>
