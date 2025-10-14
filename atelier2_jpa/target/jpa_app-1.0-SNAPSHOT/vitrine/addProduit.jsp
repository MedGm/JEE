<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Produit à la Vitrine</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            text-align: center;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .product-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 4px;
            margin-top: 10px;
            display: none;
        }
        .product-info h4 {
            margin-top: 0;
            color: #007bff;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Ajouter un Produit à la Vitrine #${vitrine.id}</h2>
        
        <form action="${pageContext.request.contextPath}/vitrine" method="post">
            <input type="hidden" name="action" value="addProduit"/>
            <input type="hidden" name="vitrineId" value="${vitrine.id}"/>
            
            <div class="form-group">
                <label for="produitId">Sélectionner un Produit:</label>
                <select id="produitId" name="produitId" required>
                    <option value="">-- Choisir un produit --</option>
                    <c:forEach var="p" items="${produits}">
                        <option value="${p.id}" 
                                data-nom="${p.nom}" 
                                data-description="${p.description}" 
                                data-prix="${p.prix}" 
                                data-stock="${p.stock}">
                            ${p.nom} - <fmt:formatNumber value="${p.prix}" type="currency" currencySymbol="DH"/> (Stock: ${p.stock})
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div id="productInfo" class="product-info">
                <h4 id="infoNom"></h4>
                <p><strong>Description:</strong> <span id="infoDescription"></span></p>
                <p><strong>Prix:</strong> <span id="infoPrix"></span> DH</p>
                <p><strong>Stock:</strong> <span id="infoStock"></span></p>
            </div>
            
            <div class="form-group">
                <input type="submit" value="Ajouter à la Vitrine" class="btn btn-primary">
                <a href="${pageContext.request.contextPath}/vitrine?action=view&id=${vitrine.id}" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $('#produitId').change(function() {
                var selectedOption = $(this).find(':selected');
                if (selectedOption.val()) {
                    $('#infoNom').text(selectedOption.data('nom'));
                    $('#infoDescription').text(selectedOption.data('description'));
                    $('#infoPrix').text(selectedOption.data('prix'));
                    $('#infoStock').text(selectedOption.data('stock'));
                    $('#productInfo').slideDown();
                } else {
                    $('#productInfo').slideUp();
                }
            });
        });
    </script>
</body>
</html>

