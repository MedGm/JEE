package ma.medgm.jpa_app.Servlet;

import ma.medgm.jpa_app.model.Vitrine;
import ma.medgm.jpa_app.model.Produit;
import ma.medgm.jpa_app.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/vitrine")
public class VitrineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "addForm":
                request.getRequestDispatcher("/vitrine/add.jsp").forward(request, response);
                break;
            case "view":
                viewVitrine(request, response);
                break;
            case "delete":
                deleteVitrine(request, response);
                break;
            case "addProduitForm":
                showAddProduitForm(request, response);
                break;
            case "removeProduit":
                removeProduit(request, response);
                break;
            default:
                listVitrines(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addVitrine(request, response);
                break;
            case "addProduit":
                addProduit(request, response);
                break;
            default:
                listVitrines(request, response);
        }
    }

    private void listVitrines(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JpaUtil.getEntityManager();
        List<Vitrine> vitrines = em.createQuery("SELECT v FROM Vitrine v", Vitrine.class).getResultList();
        em.close();

        request.setAttribute("vitrines", vitrines);
        request.getRequestDispatcher("/vitrine/list.jsp").forward(request, response);
    }

    private void addVitrine(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String nom = request.getParameter("nom");
        
        Vitrine vitrine = new Vitrine();
        vitrine.setNom(nom);
        vitrine.setProduits(new ArrayList<>());

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(vitrine);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/vitrine?action=list");
    }

    private void viewVitrine(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = JpaUtil.getEntityManager();
        Vitrine vitrine = em.find(Vitrine.class, id);
        
        // Force load products
        if (vitrine != null && vitrine.getProduits() != null) {
            vitrine.getProduits().size();
        }
        
        em.close();

        request.setAttribute("vitrine", vitrine);
        request.getRequestDispatcher("/vitrine/view.jsp").forward(request, response);
    }

    private void deleteVitrine(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Vitrine vitrine = em.find(Vitrine.class, id);
            if (vitrine != null) em.remove(vitrine);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/vitrine?action=list");
    }

    private void showAddProduitForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long vitrineId = Long.parseLong(request.getParameter("vitrineId"));

        EntityManager em = JpaUtil.getEntityManager();
        
        // Get vitrine
        Vitrine vitrine = em.find(Vitrine.class, vitrineId);
        
        // Get all available products
        List<Produit> allProduits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        
        em.close();

        request.setAttribute("vitrine", vitrine);
        request.setAttribute("produits", allProduits);
        request.getRequestDispatcher("/vitrine/addProduit.jsp").forward(request, response);
    }

    private void addProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long vitrineId = Long.parseLong(request.getParameter("vitrineId"));
        Long produitId = Long.parseLong(request.getParameter("produitId"));

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Vitrine vitrine = em.find(Vitrine.class, vitrineId);
            Produit produit = em.find(Produit.class, produitId);
            
            if (vitrine != null && produit != null) {
                if (!vitrine.getProduits().contains(produit)) {
                    vitrine.getProduits().add(produit);
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/vitrine?action=view&id=" + vitrineId);
    }

    private void removeProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long vitrineId = Long.parseLong(request.getParameter("vitrineId"));
        Long produitId = Long.parseLong(request.getParameter("produitId"));

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Vitrine vitrine = em.find(Vitrine.class, vitrineId);
            Produit produit = em.find(Produit.class, produitId);
            
            if (vitrine != null && produit != null) {
                vitrine.getProduits().remove(produit);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/vitrine?action=view&id=" + vitrineId);
    }
}

