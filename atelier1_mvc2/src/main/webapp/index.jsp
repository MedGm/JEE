<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Gestion des Commandes</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <style>
    .card-hover:hover { transform: translateY(-4px); box-shadow: 0 0.5rem 1rem rgba(0,0,0,.15); }
  </style>
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

  <header class="bg-light py-5 mb-4 border-bottom">
    <div class="container">
      <h1 class="display-5 fw-bold">Gestion des Commandes</h1>
      <p class="lead mb-0">Accédez aux espaces Clients, Produits et Commandes.</p>
    </div>
  </header>

  <main class="container pb-5">
    <div class="row g-4">
      <div class="col-md-4">
        <a href="${pageContext.request.contextPath}/clients" class="text-decoration-none">
          <div class="card h-100 card-hover">
            <div class="card-body">
              <h5 class="card-title">Espace Clients</h5>
              <p class="card-text">Lister, ajouter et gérer les clients.</p>
            </div>
            <div class="card-footer bg-transparent border-top-0">
              <span class="btn btn-primary">Accéder</span>
            </div>
          </div>
        </a>
      </div>
      <div class="col-md-4">
        <a href="${pageContext.request.contextPath}/produits" class="text-decoration-none">
          <div class="card h-100 card-hover">
            <div class="card-body">
              <h5 class="card-title">Espace Produits</h5>
              <p class="card-text">Lister, ajouter et gérer les produits.</p>
            </div>
            <div class="card-footer bg-transparent border-top-0">
              <span class="btn btn-primary">Accéder</span>
            </div>
          </div>
        </a>
      </div>
      <div class="col-md-4">
        <a href="${pageContext.request.contextPath}/commandes" class="text-decoration-none">
          <div class="card h-100 card-hover">
            <div class="card-body">
              <h5 class="card-title">Espace Commandes</h5>
              <p class="card-text">Lister, ajouter et gérer les commandes.</p>
            </div>
            <div class="card-footer bg-transparent border-top-0">
              <span class="btn btn-primary">Accéder</span>
            </div>
          </div>
        </a>
      </div>
    </div>
  </main>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>