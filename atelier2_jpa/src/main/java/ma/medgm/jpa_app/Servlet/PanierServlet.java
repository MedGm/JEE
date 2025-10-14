package ma.medgm.jpa_app.Servlet;

import ma.medgm.jpa_app.model.Panier;
import ma.medgm.jpa_app.model.Produit;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ma.medgm.jpa_app.util.JpaUtil;

import java.io.IOException;

@WebServlet("/panier")
public class PanierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "afficher";

        switch (action) {
            case "ajouter":
                ajouterProduit(request, response);
                break;
            case "supprimer":
                supprimerProduit(request, response);
                break;
            case "afficher":
            default:
                afficherPanier(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("ajouter".equals(action)) {
            ajouterProduit(request, response);
        }
    }

    private void afficherPanier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");
        if (panier == null) {
            panier = new Panier();
            session.setAttribute("panier", panier);
        }

        request.setAttribute("panier", panier);
        request.getRequestDispatcher("/panier/list.jsp").forward(request, response);
    }

    private void ajouterProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        EntityManager em = JpaUtil.getEntityManager();
        Produit produit = em.find(Produit.class, id);
        em.close();

        if (produit != null) {
            HttpSession session = request.getSession();
            Panier panier = (Panier) session.getAttribute("panier");
            if (panier == null) {
                panier = new Panier();
                session.setAttribute("panier", panier);
            }
            panier.ajouterProduit(produit);
        }

        // Redirect back to product list with success message
        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("/produit")) {
            response.sendRedirect(request.getContextPath() + "/produit?action=list&added=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/panier?action=afficher");
        }
    }

    private void supprimerProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");

        if (panier != null) {
            panier.supprimerProduit(id);
        }

        response.sendRedirect(request.getContextPath() + "/panier?action=afficher");
    }
}

