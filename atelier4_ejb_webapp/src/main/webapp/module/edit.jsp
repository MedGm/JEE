<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Module" %>
<html>
<head>
    <title>Modifier Module</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Modifier un Module</h2>
        <% Module module = (Module) request.getAttribute("module"); %>
        <form method="POST" action="edit">
            <input type="hidden" name="id" value="<%= module.getIdModule() %>">
            <div class="form-group">
                <label for="nomModule">Nom du Module:</label>
                <input type="text" class="form-control" id="nomModule" name="nomModule" value="<%= module.getNomModule() %>" required>
            </div>
            <button type="submit" class="btn btn-primary">Modifier</button>
            <a href="list" class="btn btn-secondary">Annuler</a>
        </form>
    </div>
</body>
</html>

