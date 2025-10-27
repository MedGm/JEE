<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter Etudiant</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Ajouter un Etudiant</h2>
        <form method="POST" action="add">
            <div class="form-group">
                <label for="nom">Nom:</label>
                <input type="text" class="form-control" id="nom" name="nom" required>
            </div>
            <div class="form-group">
                <label for="prenom">Prénom:</label>
                <input type="text" class="form-control" id="prenom" name="prenom" required>
            </div>
            <div class="form-group">
                <label for="cne">CNE:</label>
                <input type="text" class="form-control" id="cne" name="cne" required>
            </div>
            <div class="form-group">
                <label for="adresse">Adresse:</label>
                <input type="text" class="form-control" id="adresse" name="adresse">
            </div>
            <div class="form-group">
                <label for="niveau">Niveau:</label>
                <input type="text" class="form-control" id="niveau" name="niveau">
            </div>
            <button type="submit" class="btn btn-primary">Ajouter</button>
            <a href="list" class="btn btn-secondary">Annuler</a>
        </form>
    </div>
</body>
</html>
