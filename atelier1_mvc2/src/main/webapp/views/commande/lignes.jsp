<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Lignes de commande</title>
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
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/commandes">Commandes</a></li>
            </ul>
        </div>
    </div>
  </nav>

<main class="container py-4">
    <a href="${pageContext.request.contextPath}/commandes" class="btn btn-outline-secondary mb-3">Retour</a>
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">${error}</div>
    </c:if>

    <c:if test="${not empty client}">
        <div class="mb-3">
            <h2 class="h4">Commandes du client #${client.client_id} - ${client.nom}</h2>
        </div>
    </c:if>

    <c:forEach var="cmd" items="${commandes}">
        <div class="card mb-4">
            <div class="card-header d-flex justify-content-between align-items-center">
                <div>
                    <strong>Commande #${cmd.commande_id}</strong>
                    <span class="text-muted ms-2">${cmd.date_commande}</span>
                </div>
                <div>Total: <strong>${cmd.total}</strong></div>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-sm mb-0">
                        <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Produit ID</th>
                            <th>Quantité</th>
                            <th>Prix Unitaire</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="lignes" value="${lignesMap[cmd.commande_id]}" />
                        <c:forEach var="ligne" items="${lignes}">
                            <tr>
                                <td>${ligne.id}</td>
                                <td>${ligne.produitId}</td>
                                <td>${ligne.quantite}</td>
                                <td>${ligne.prixUnitaire}</td>
                                <td class="text-end">
                                    <form method="post" action="${pageContext.request.contextPath}/lignes" class="d-inline">
                                        <input type="hidden" name="action" value="delete-line" />
                                        <input type="hidden" name="clientId" value="${client.client_id}" />
                                        <input type="hidden" name="commandeId" value="${cmd.commande_id}" />
                                        <input type="hidden" name="ligneId" value="${ligne.id}" />
                                        <button type="submit" class="btn btn-sm btn-outline-danger">Supprimer</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="card-body border-top">
                <form method="post" action="${pageContext.request.contextPath}/lignes" class="row g-3">
                    <input type="hidden" name="action" value="add-line" />
                    <input type="hidden" name="clientId" value="${client.client_id}" />
                    <input type="hidden" name="commandeId" value="${cmd.commande_id}" />
                    <div class="col-md-3">
                        <label class="form-label">Produit ID</label>
                        <input type="number" name="produitId" class="form-control" required />
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Quantité</label>
                        <input type="number" name="quantite" min="1" value="1" class="form-control" required />
                    </div>
                    <div class="col-md-3 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary">Ajouter la ligne</button>
                    </div>
                </form>
            </div>
        </div>
    </c:forEach>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>


