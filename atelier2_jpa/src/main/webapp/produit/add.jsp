<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Produit</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .form-container {
            max-width: 500px;
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
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="text"]:focus,
        input[type="number"]:focus {
            outline: none;
            border-color: #007bff;
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
        .error {
            color: red;
            font-size: 12px;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Ajouter un Produit</h2>
        <form id="produitForm" action="${pageContext.request.contextPath}/produit" method="post">
            <input type="hidden" name="action" value="add"/>
            
            <div class="form-group">
                <label for="nom">Nom:</label>
                <input type="text" id="nom" name="nom" required>
                <span class="error" id="nomError"></span>
            </div>
            
            <div class="form-group">
                <label for="description">Description:</label>
                <input type="text" id="description" name="description" required>
                <span class="error" id="descriptionError"></span>
            </div>
            
            <div class="form-group">
                <label for="prix">Prix (DH):</label>
                <input type="number" id="prix" name="prix" step="0.01" min="0" required>
                <span class="error" id="prixError"></span>
            </div>
            
            <div class="form-group">
                <label for="stock">Stock:</label>
                <input type="number" id="stock" name="stock" min="0" required>
                <span class="error" id="stockError"></span>
            </div>
            
            <div class="form-group">
                <input type="submit" value="Ajouter" class="btn btn-primary">
                <a href="${pageContext.request.contextPath}/produit?action=list" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $('#produitForm').submit(function(e) {
                var isValid = true;
                $('.error').text('');
                
                // Validate nom
                if ($('#nom').val().trim().length < 3) {
                    $('#nomError').text('Le nom doit contenir au moins 3 caractères');
                    isValid = false;
                }
                
                // Validate description
                if ($('#description').val().trim().length < 5) {
                    $('#descriptionError').text('La description doit contenir au moins 5 caractères');
                    isValid = false;
                }
                
                // Validate prix
                var prix = parseFloat($('#prix').val());
                if (isNaN(prix) || prix <= 0) {
                    $('#prixError').text('Le prix doit être supérieur à 0');
                    isValid = false;
                }
                
                // Validate stock
                var stock = parseInt($('#stock').val());
                if (isNaN(stock) || stock < 0) {
                    $('#stockError').text('Le stock ne peut pas être négatif');
                    isValid = false;
                }
                
                if (!isValid) {
                    e.preventDefault();
                }
            });
        });
    </script>
</body>
</html>
