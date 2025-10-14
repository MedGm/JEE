<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier un Internaute</title>
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
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus {
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
        .info {
            background-color: #d1ecf1;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            color: #0c5460;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Modifier l'Internaute</h2>
        <form id="internauteForm" action="${pageContext.request.contextPath}/internaute" method="post">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="id" value="${internaute.id}"/>
            
            <div class="form-group">
                <label for="nom">Nom:</label>
                <input type="text" id="nom" name="nom" value="${internaute.nom}" required>
                <span class="error" id="nomError"></span>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${internaute.email}" required>
                <span class="error" id="emailError"></span>
            </div>
            
            <div class="info">
                Laissez le mot de passe vide pour ne pas le modifier
            </div>
            
            <div class="form-group">
                <label for="motDePasse">Nouveau mot de passe (optionnel):</label>
                <input type="password" id="motDePasse" name="motDePasse">
                <span class="error" id="passwordError"></span>
            </div>
            
            <div class="form-group">
                <input type="submit" value="Modifier" class="btn btn-primary">
                <a href="${pageContext.request.contextPath}/internaute?action=list" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $('#internauteForm').submit(function(e) {
                var isValid = true;
                $('.error').text('');
                
                // Validate nom
                if ($('#nom').val().trim().length < 3) {
                    $('#nomError').text('Le nom doit contenir au moins 3 caractères');
                    isValid = false;
                }
                
                // Validate email
                var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailPattern.test($('#email').val())) {
                    $('#emailError').text('Email invalide');
                    isValid = false;
                }
                
                // Validate password only if provided
                if ($('#motDePasse').val().length > 0 && $('#motDePasse').val().length < 6) {
                    $('#passwordError').text('Le mot de passe doit contenir au moins 6 caractères');
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

