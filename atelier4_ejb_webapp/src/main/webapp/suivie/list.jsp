<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Suivie" %>
<html>
<head>
    <title>Liste des Suivies (Notes)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Liste des Notes</h2>
        <a href="add" class="btn btn-success mb-3">Ajouter une Note</a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Etudiant</th>
                    <th>Module</th>
                    <th>Note</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Suivie> suivies = (List<Suivie>) request.getAttribute("suivies");
                    if (suivies != null) {
                        for (Suivie s : suivies) {
                %>
                <tr>
                    <td><%= s.getIdSuivie() %></td>
                    <td>
                        <% if (s.getEtudiant() != null) { %>
                            <%= s.getEtudiant().getNom() %> <%= s.getEtudiant().getPrenom() %> (<%= s.getEtudiant().getCne() %>)
                        <% } else { %>
                            N/A
                        <% } %>
                    </td>
                    <td>
                        <% if (s.getModule() != null) { %>
                            <%= s.getModule().getNomModule() %>
                        <% } else { %>
                            N/A
                        <% } %>
                    </td>
                    <td><%= s.getNote() %></td>
                    <td><%= s.getDate() %></td>
                    <td>
                        <a href="edit?id=<%= s.getIdSuivie() %>" class="btn btn-sm btn-warning">Modifier</a>
                        <a href="delete?id=<%= s.getIdSuivie() %>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette note?')">Supprimer</a>
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
