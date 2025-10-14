package ma.medgm.jpa_app.Servlet;

import ma.medgm.jpa_app.model.Internaute;
import ma.medgm.jpa_app.model.Panier;
import ma.medgm.jpa_app.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/internaute")
public class InternauteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "addForm":
                request.getRequestDispatcher("/internaute/add.jsp").forward(request, response);
                break;
            case "editForm":
                showEditForm(request, response);
                break;
            case "delete":
                deleteInternaute(request, response);
                break;
            default:
                listInternautes(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addInternaute(request, response);
                break;
            case "update":
                updateInternaute(request, response);
                break;
            default:
                listInternautes(request, response);
        }
    }

    private void listInternautes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JpaUtil.getEntityManager();
        List<Internaute> internautes = em.createQuery("SELECT i FROM Internaute i", Internaute.class).getResultList();
        em.close();

        request.setAttribute("internautes", internautes);
        request.getRequestDispatcher("/internaute/list.jsp").forward(request, response);
    }

    private void addInternaute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Internaute internaute = new Internaute();
        internaute.setNom(nom);
        internaute.setEmail(email);
        internaute.setMotDePasse(motDePasse);
        
        // Create a new empty cart for the user
        Panier panier = new Panier();
        internaute.setPanier(panier);

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(internaute);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/internaute?action=list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = JpaUtil.getEntityManager();
        Internaute internaute = em.find(Internaute.class, id);
        em.close();

        request.setAttribute("internaute", internaute);
        request.getRequestDispatcher("/internaute/edit.jsp").forward(request, response);
    }

    private void updateInternaute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Internaute internaute = em.find(Internaute.class, id);
            if (internaute != null) {
                internaute.setNom(nom);
                internaute.setEmail(email);
                if (motDePasse != null && !motDePasse.isEmpty()) {
                    internaute.setMotDePasse(motDePasse);
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/internaute?action=list");
    }

    private void deleteInternaute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Internaute internaute = em.find(Internaute.class, id);
            if (internaute != null) em.remove(internaute);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/internaute?action=list");
    }
}

