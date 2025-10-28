<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Suivie" %>
<%@ page import="entities.Etudiant" %>
<%@ page import="entities.Module" %>
<html>
<head>
    <title>Modifier Suivie (Note)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Modifier une Note</h2>
        <% Suivie suivie = (Suivie) request.getAttribute("suivie"); %>
        <form method="POST" action="edit">
            <input type="hidden" name="id" value="<%= suivie.getIdSuivie() %>">
            <div class="form-group">
                <label for="note">Note:</label>
                <input type="number" step="0.01" class="form-control" id="note" name="note" value="<%= suivie.getNote() %>" required min="0" max="20">
            </div>
            <div class="form-group">
                <label for="date">Date:</label>
                <input type="date" class="form-control" id="date" name="date" value="<%= suivie.getDate() %>" required>
            </div>
            <div class="form-group">
                <label for="idEtudiant">Etudiant:</label>
                <select class="form-control" id="idEtudiant" name="idEtudiant" required>
                    <% 
                        List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
                        if (etudiants != null) {
                            for (Etudiant e : etudiants) {
                                boolean selected = e.getIdEtudiant().equals(suivie.getEtudiant().getIdEtudiant());
                    %>
                    <option value="<%= e.getIdEtudiant() %>" <% if(selected) { %>selected<% } %>><%= e.getNom() %> <%= e.getPrenom() %> (<%= e.getCne() %>)</option>
                    <% 
                            }
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="idModule">Module:</label>
                <select class="form-control" id="idModule" name="idModule" required>
                    <% 
                        List<Module> modules = (List<Module>) request.getAttribute("modules");
                        if (modules != null) {
                            for (Module m : modules) {
                                boolean selected = m.getIdModule().equals(suivie.getModule().getIdModule());
                    %>
                    <option value="<%= m.getIdModule() %>" <% if(selected) { %>selected<% } %>><%= m.getNomModule() %></option>
                    <% 
                            }
                        }
                    %>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Modifier</button>
            <a href="list" class="btn btn-secondary">Annuler</a>
        </form>
    </div>
</body>
</html>

