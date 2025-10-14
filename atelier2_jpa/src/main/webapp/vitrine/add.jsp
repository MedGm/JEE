<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Créer une Vitrine</title>
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
        .info {
            background-color: #d1ecf1;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            color: #0c5460;
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
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="text"]:focus {
            outline: none;
            border-color: #007bff;
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
        <h2>Créer une Vitrine</h2>
        <form id="vitrineForm" action="${pageContext.request.contextPath}/vitrine" method="post">
            <input type="hidden" name="action" value="add"/>
            
            <div class="form-group">
                <label for="nom">Nom de la Vitrine:</label>
                <input type="text" id="nom" name="nom" placeholder="Ex: Vitrine Promotions, Nouveautés..." required>
                <span class="error" id="nomError"></span>
            </div>
            
            <div class="info">
                Vous pourrez ajouter des produits après la création.
            </div>
            
            <div class="form-group">
                <input type="submit" value="Créer la Vitrine" class="btn btn-primary">
                <a href="${pageContext.request.contextPath}/vitrine?action=list" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $('#vitrineForm').submit(function(e) {
                var isValid = true;
                $('.error').text('');
                
                // Validate nom
                if ($('#nom').val().trim().length < 3) {
                    $('#nomError').text('Le nom doit contenir au moins 3 caractères');
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

