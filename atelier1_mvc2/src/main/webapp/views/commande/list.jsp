<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Commandes</title>
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
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/produits">Produits</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/commandes">Commandes</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <main class="container py-4">
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>
        <div class="d-flex align-items-center justify-content-between mb-3">
            <h1 class="h3 mb-0">Liste des Commandes</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">Retour à l'accueil</a>
        </div>

        <div class="card mb-4">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-striped table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Date</th>
                                <th>Client ID</th>
                                <th>Total</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="commande" items="${commandes}">
                                <tr>
                                    <td>${commande.commande_id}</td>
                                    <td>${commande.date_commande}</td>
                                    <td>${commande.client_id.client_id}</td>
                                    <td>${commande.total}</td>
                                    <td>
                                        <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/lignes?clientId=${commande.client_id.client_id}">Détails</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <c:if test="${not empty lignes}">
            <div class="card mb-4">
                <div class="card-header">Lignes de commande</div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-sm table-striped mb-0">
                            <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Produit ID</th>
                                <th>Quantité</th>
                                <th>Prix Unitaire</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="ligne" items="${lignes}">
                                <tr>
                                    <td>${ligne.id}</td>
                                    <td>${ligne.produitId}</td>
                                    <td>${ligne.quantite}</td>
                                    <td>${ligne.prixUnitaire}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:if>

        <div class="card">
            <div class="card-header">Ajouter une Commande</div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/commandes" class="row g-3">
                    <input type="hidden" name="action" value="add" />
                    <div class="col-md-6">
                        <label class="form-label">Date</label>
                        <input type="datetime-local" name="date_commande" class="form-control" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Client ID</label>
                        <input type="number" name="client_id" class="form-control" required />
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
