<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails de la Vitrine</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
        }
        .btn {
            padding: 8px 16px;
            margin: 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        .btn-success:hover {
            background-color: #218838;
        }
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .nav-menu {
            background-color: #343a40;
            padding: 10px;
            margin-bottom: 20px;
        }
        .nav-menu a {
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            display: inline-block;
        }
        .nav-menu a:hover {
            background-color: #495057;
        }
        .empty-message {
            text-align: center;
            padding: 40px;
            color: #666;
            font-style: italic;
        }
    </style>
</head>
<body>
    <div class="nav-menu">
        <a href="${pageContext.request.contextPath}/produit?action=list">Produits</a>
        <a href="${pageContext.request.contextPath}/internaute?action=list">Internautes</a>
        <a href="${pageContext.request.contextPath}/vitrine?action=list">Vitrines</a>
        <a href="${pageContext.request.contextPath}/panier?action=afficher">Panier</a>
    </div>

    <div class="container">
        <h2>${vitrine.nom}</h2>
        <p style="color: #666; font-style: italic;">Vitrine #${vitrine.id}</p>
        
        <div style="margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/vitrine?action=addProduitForm&vitrineId=${vitrine.id}" class="btn btn-success">Ajouter un Produit</a>
            <a href="${pageContext.request.contextPath}/vitrine?action=list" class="btn btn-secondary">Retour à la liste</a>
        </div>

        <h3>Produits dans la vitrine (${vitrine.produits.size()})</h3>
        
        <c:choose>
            <c:when test="${empty vitrine.produits}">
                <div class="empty-message">
                    Aucun produit dans cette vitrine. Cliquez sur "Ajouter un Produit" pour commencer.
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nom</th>
                            <th>Description</th>
                            <th>Prix</th>
                            <th>Stock</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${vitrine.produits}">
                            <tr>
                                <td>${p.id}</td>
                                <td>${p.nom}</td>
                                <td>${p.description}</td>
                                <td><fmt:formatNumber value="${p.prix}" type="currency" currencySymbol="DH"/></td>
                                <td>${p.stock}</td>
                                <td>
                                    <a href="#" class="btn btn-danger remove-btn" data-id="${p.id}">Retirer</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        $(document).ready(function() {
            $('.remove-btn').click(function(e) {
                e.preventDefault();
                var produitId = $(this).data('id');
                if (confirm('Voulez-vous retirer ce produit de la vitrine?')) {
                    window.location.href = '${pageContext.request.contextPath}/vitrine?action=removeProduit&vitrineId=${vitrine.id}&produitId=' + produitId;
                }
            });
        });
    </script>
</body>
</html>

