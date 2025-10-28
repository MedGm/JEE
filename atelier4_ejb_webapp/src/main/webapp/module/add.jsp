<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter Module</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Ajouter un Module</h2>
        <form method="POST" action="add">
            <div class="form-group">
                <label for="nomModule">Nom du Module:</label>
                <input type="text" class="form-control" id="nomModule" name="nomModule" required>
            </div>
            <button type="submit" class="btn btn-primary">Ajouter</button>
            <a href="list" class="btn btn-secondary">Annuler</a>
        </form>
    </div>
</body>
</html>
