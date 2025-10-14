package ma.medgm.jpa_app.Servlet;

import ma.medgm.jpa_app.model.Produit;
import ma.medgm.jpa_app.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/produit")
public class ProduitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "addForm":
                request.getRequestDispatcher("/produit/add.jsp").forward(request, response);
                break;
            case "delete":
                deleteProduit(request, response);
                break;
            default:
                listProduits(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addProduit(request, response);
        }
    }

    private void listProduits(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JpaUtil.getEntityManager();
        List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        em.close();

        request.setAttribute("produits", produits);
        request.getRequestDispatcher("/produit/list.jsp").forward(request, response);
    }

    private void addProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        double prix = Double.parseDouble(request.getParameter("prix"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Produit produit = new Produit(nom, description, prix, stock);

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(produit);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/produit?action=list");
    }

    private void deleteProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Produit produit = em.find(Produit.class, id);
            if (produit != null) em.remove(produit);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/produit?action=list");
    }
}
