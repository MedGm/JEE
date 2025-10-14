<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Produits</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        h2 {
            color: #333;
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
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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
        .search-box {
            margin-bottom: 20px;
            padding: 10px;
            background-color: white;
            border-radius: 4px;
        }
        .search-box input {
            padding: 8px;
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .stock-low {
            color: #dc3545;
            font-weight: bold;
        }
        .stock-ok {
            color: #28a745;
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

    <h2>Liste des Produits</h2>
    
    <div class="search-box">
        <input type="text" id="searchInput" placeholder="Rechercher un produit...">
    </div>
    
    <a href="${pageContext.request.contextPath}/produit?action=addForm" class="btn btn-primary">Ajouter un Produit</a>
    
    <table id="produitTable">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Description</th>
                <th>Prix</th>
                <th>Stock</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${produits}">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nom}</td>
                    <td>${p.description}</td>
                    <td><fmt:formatNumber value="${p.prix}" type="currency" currencySymbol="DH"/></td>
                    <td class="${p.stock < 10 ? 'stock-low' : 'stock-ok'}">${p.stock}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/panier?action=ajouter&id=${p.id}" class="btn btn-success">Ajouter au Panier</a>
                        <a href="#" class="btn btn-danger delete-btn" data-id="${p.id}">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script>
        $(document).ready(function() {
            // Search functionality
            $('#searchInput').on('keyup', function() {
                var value = $(this).val().toLowerCase();
                $('#produitTable tbody tr').filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });

            // Delete confirmation
            $('.delete-btn').click(function(e) {
                e.preventDefault();
                var id = $(this).data('id');
                if (confirm('Êtes-vous sûr de vouloir supprimer ce produit?')) {
                    window.location.href = '${pageContext.request.contextPath}/produit?action=delete&id=' + id;
                }
            });

            // Check if we have a success parameter
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get('added') === 'true') {
                alert('✓ Produit ajouté au panier avec succès!');
                // Remove the parameter from URL
                window.history.replaceState({}, document.title, window.location.pathname + '?action=list');
            }
        });
    </script>
</body>
</html>
