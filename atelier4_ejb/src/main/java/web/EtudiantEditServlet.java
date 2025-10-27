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

@WebServlet("/etudiant/edit")
public class EtudiantEditServlet extends HttpServlet {
    @EJB
    private EtudiantServiceRemote etudiantService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Etudiant etudiant = etudiantService.trouverEtudiantParId(id);
        req.setAttribute("etudiant", etudiant);
        req.getRequestDispatcher("/etudiant/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Etudiant etudiant = etudiantService.trouverEtudiantParId(id);
        
        etudiant.setNom(req.getParameter("nom"));
        etudiant.setPrenom(req.getParameter("prenom"));
        etudiant.setCne(req.getParameter("cne"));
        etudiant.setAdresse(req.getParameter("adresse"));
        etudiant.setNiveau(req.getParameter("niveau"));
        
        etudiantService.modifierEtudiant(etudiant);
        resp.sendRedirect("list");
    }
}

