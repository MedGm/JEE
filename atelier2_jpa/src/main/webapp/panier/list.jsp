<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mon Panier</title>
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
            padding: 60px;
            color: #666;
            font-style: italic;
            font-size: 18px;
        }
        .total-section {
            margin-top: 30px;
            text-align: right;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
        .total-section h3 {
            color: #28a745;
            margin: 0;
        }
        .quantity-display {
            font-weight: bold;
            color: #007bff;
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
        <h2>Mon Panier</h2>
        
        <c:choose>
            <c:when test="${empty panier.lignes}">
                <div class="empty-message">
                    🛒 Votre panier est vide
                    <br><br>
                    <a href="${pageContext.request.contextPath}/produit?action=list" class="btn btn-success">Voir les Produits</a>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Produit</th>
                            <th>Description</th>
                            <th>Prix Unitaire</th>
                            <th>Quantité</th>
                            <th>Sous-total</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ligne" items="${panier.lignes}">
                            <tr>
                                <td>${ligne.produit.nom}</td>
                                <td>${ligne.produit.description}</td>
                                <td><fmt:formatNumber value="${ligne.produit.prix}" type="currency" currencySymbol="DH"/></td>
                                <td class="quantity-display">${ligne.quantite}</td>
                                <td><fmt:formatNumber value="${ligne.produit.prix * ligne.quantite}" type="currency" currencySymbol="DH"/></td>
                                <td>
                                    <a href="#" class="btn btn-danger remove-btn" data-id="${ligne.produit.id}">Retirer</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="total-section">
                    <h3>Total: <fmt:formatNumber value="${panier.total}" type="currency" currencySymbol="DH"/></h3>
                </div>
                
                <div style="margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/produit?action=list" class="btn btn-success">Continuer mes Achats</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        $(document).ready(function() {
            $('.remove-btn').click(function(e) {
                e.preventDefault();
                var produitId = $(this).data('id');
                if (confirm('Voulez-vous retirer ce produit du panier?')) {
                    window.location.href = '${pageContext.request.contextPath}/panier?action=supprimer&id=' + produitId;
                }
            });
        });
    </script>
</body>
</html>
