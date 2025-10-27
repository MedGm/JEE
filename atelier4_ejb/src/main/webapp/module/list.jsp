<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Module" %>
<html>
<head>
    <title>Liste des Modules</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Liste des Modules</h2>
        <a href="add" class="btn btn-success mb-3">Ajouter un Module</a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom du Module</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Module> modules = (List<Module>) request.getAttribute("modules");
                    if (modules != null) {
                        for (Module m : modules) {
                %>
                <tr>
                    <td><%= m.getIdModule() %></td>
                    <td><%= m.getNomModule() %></td>
                    <td>
                        <a href="edit?id=<%= m.getIdModule() %>" class="btn btn-sm btn-warning">Modifier</a>
                        <a href="delete?id=<%= m.getIdModule() %>" class="btn btn-sm btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce module?')">Supprimer</a>
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
