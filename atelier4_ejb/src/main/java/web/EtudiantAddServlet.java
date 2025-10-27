package web;

import entities.Etudiant;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EtudiantServiceRemote;

import java.io.IOException;

@WebServlet("/etudiant/add")
public class EtudiantAddServlet extends HttpServlet {
    @EJB
    private EtudiantServiceRemote etudiantService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/etudiant/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Etudiant e = new Etudiant();
        e.setNom(req.getParameter("nom"));
        e.setPrenom(req.getParameter("prenom"));
        e.setCne(req.getParameter("cne"));
        e.setAdresse(req.getParameter("adresse"));
        e.setNiveau(req.getParameter("niveau"));
        etudiantService.ajouterEtudiant(e);
        resp.sendRedirect("list");
    }
}