<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Application E-Commerce JPA</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .container {
            max-width: 1200px;
            background-color: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            overflow: hidden;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px;
            text-align: center;
        }
        .header h1 {
            font-size: 48px;
            margin-bottom: 10px;
        }
        .header p {
            font-size: 18px;
            opacity: 0.9;
        }
        .content {
            padding: 40px;
        }
        .modules-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 30px;
            margin-top: 30px;
        }
        .module-card {
            background: white;
            border: 2px solid #e0e0e0;
            border-radius: 15px;
            padding: 30px;
            text-align: center;
            transition: all 0.3s ease;
            cursor: pointer;
        }
        .module-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            border-color: #667eea;
        }
        .module-icon {
            font-size: 60px;
            margin-bottom: 20px;
        }
        .module-card h3 {
            color: #333;
            font-size: 24px;
            margin-bottom: 15px;
        }
        .module-card p {
            color: #666;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            padding: 12px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 25px;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        .btn:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .features {
            margin-top: 40px;
            padding: 30px;
            background-color: #f8f9fa;
            border-radius: 15px;
        }
        .features h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .features ul {
            list-style: none;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }
        .features li {
            padding: 10px;
            background-color: white;
            border-radius: 8px;
            text-align: center;
        }
        .features li:before {
            content: "✓";
            color: #28a745;
            font-weight: bold;
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🛒 E-Commerce JPA</h1>
            <p>Application de gestion e-commerce avec Jakarta EE & JPA</p>
        </div>
        
        <div class="content">
            <div class="modules-grid">
                <div class="module-card" onclick="location.href='${pageContext.request.contextPath}/produit?action=list'">
                    <div class="module-icon">📦</div>
                    <h3>Produits</h3>
                    <p>Gérer le catalogue de produits</p>
                    <a href="${pageContext.request.contextPath}/produit?action=list" class="btn">Accéder</a>
                </div>
                
                <div class="module-card" onclick="location.href='${pageContext.request.contextPath}/internaute?action=list'">
                    <div class="module-icon">👥</div>
                    <h3>Internautes</h3>
                    <p>Gérer les utilisateurs</p>
                    <a href="${pageContext.request.contextPath}/internaute?action=list" class="btn">Accéder</a>
                </div>
                
                <div class="module-card" onclick="location.href='${pageContext.request.contextPath}/vitrine?action=list'">
                    <div class="module-icon">🏪</div>
                    <h3>Vitrines</h3>
                    <p>Gérer les vitrines de produits</p>
                    <a href="${pageContext.request.contextPath}/vitrine?action=list" class="btn">Accéder</a>
                </div>
                
                <div class="module-card" onclick="location.href='${pageContext.request.contextPath}/panier?action=afficher'">
                    <div class="module-icon">🛍️</div>
                    <h3>Panier</h3>
                    <p>Voir le panier d'achat</p>
                    <a href="${pageContext.request.contextPath}/panier?action=afficher" class="btn">Accéder</a>
                </div>
            </div>
            
            <div class="features">
                <h2>Fonctionnalités de l'application</h2>
                <ul>
                    <li>Architecture MVC 2</li>
                    <li>JPA avec EclipseLink</li>
                    <li>CRUD complet</li>
                    <li>Servlets multi-actions</li>
                    <li>JSTL pour les vues</li>
                    <li>jQuery pour l'UX</li>
                    <li>Design responsive</li>
                    <li>Validation des formulaires</li>
                </ul>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            // Add animation on page load
            $('.module-card').each(function(i) {
                $(this).css('opacity', '0').delay(i * 100).animate({ opacity: 1 }, 500);
            });
        });
    </script>
</body>
</html>
