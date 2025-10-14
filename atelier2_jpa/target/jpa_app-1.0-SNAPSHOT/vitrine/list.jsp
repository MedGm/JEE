<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Vitrines</title>
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
    </style>
</head>
<body>
    <div class="nav-menu">
        <a href="${pageContext.request.contextPath}/produit?action=list">Produits</a>
        <a href="${pageContext.request.contextPath}/internaute?action=list">Internautes</a>
        <a href="${pageContext.request.contextPath}/vitrine?action=list">Vitrines</a>
        <a href="${pageContext.request.contextPath}/panier?action=afficher">Panier</a>
    </div>

    <h2>Liste des Vitrines</h2>
    <a href="${pageContext.request.contextPath}/vitrine?action=addForm" class="btn btn-primary">Créer une Vitrine</a>
    
    <table id="vitrineTable">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Nombre de Produits</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="v" items="${vitrines}">
                <tr>
                    <td>${v.id}</td>
                    <td>${v.nom}</td>
                    <td>${v.produits.size()}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/vitrine?action=view&id=${v.id}" class="btn btn-success">Voir Détails</a>
                        <a href="#" class="btn btn-danger delete-btn" data-id="${v.id}">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script>
        $(document).ready(function() {
            $('.delete-btn').click(function(e) {
                e.preventDefault();
                var id = $(this).data('id');
                if (confirm('Êtes-vous sûr de vouloir supprimer cette vitrine?')) {
                    window.location.href = '${pageContext.request.contextPath}/vitrine?action=delete&id=' + id;
                }
            });
        });
    </script>
</body>
</html>

