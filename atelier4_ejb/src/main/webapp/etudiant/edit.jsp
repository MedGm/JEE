<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Etudiant" %>
<html>
<head>
    <title>Modifier Etudiant</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Modifier un Etudiant</h2>
        <% Etudiant etudiant = (Etudiant) request.getAttribute("etudiant"); %>
        <form method="POST" action="edit">
            <input type="hidden" name="id" value="${etudiant.idEtudiant}">
            <div class="form-group">
                <label for="nom">Nom:</label>
                <input type="text" class="form-control" id="nom" name="nom" value="${etudiant.nom}" required>
            </div>
            <div class="form-group">
                <label for="prenom">Prénom:</label>
                <input type="text" class="form-control" id="prenom" name="prenom" value="${etudiant.prenom}" required>
            </div>
            <div class="form-group">
                <label for="cne">CNE:</label>
                <input type="text" class="form-control" id="cne" name="cne" value="${etudiant.cne}" required>
            </div>
            <div class="form-group">
                <label for="adresse">Adresse:</label>
                <input type="text" class="form-control" id="adresse" name="adresse" value="${etudiant.adresse}">
            </div>
            <div class="form-group">
                <label for="niveau">Niveau:</label>
                <input type="text" class="form-control" id="niveau" name="niveau" value="${etudiant.niveau}">
            </div>
            <button type="submit" class="btn btn-primary">Modifier</button>
            <a href="list" class="btn btn-secondary">Annuler</a>
        </form>
    </div>
</body>
</html>
