<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Produits</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Atelier MVC2</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/clients">Clients</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/produits">Produits</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/commandes">Commandes</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <main class="container py-4">
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>
        <div class="d-flex align-items-center justify-content-between mb-3">
            <h1 class="h3 mb-0">Liste des Produits</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">Retour à l'accueil</a>
        </div>

        <div class="card mb-4">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-striped table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Prix</th>
                                <th>Stock</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="produit" items="${produits}">
                                <tr>
                                    <td>${produit.id}</td>
                                    <td>${produit.nom}</td>
                                    <td>${produit.prix}</td>
                                    <td>${produit.stock}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header">Ajouter un Produit</div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/produits" class="row g-3">
                    <input type="hidden" name="action" value="add" />
                    <div class="col-md-4">
                        <label class="form-label">Nom</label>
                        <input type="text" name="nom" class="form-control" required />
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Prix</label>
                        <input type="number" step="0.01" name="prix" class="form-control" required />
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Stock</label>
                        <input type="number" name="stock" class="form-control" required />
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">Ajouter</button>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
