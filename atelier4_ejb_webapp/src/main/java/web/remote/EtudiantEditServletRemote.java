package web.remote;

import entities.Etudiant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EtudiantServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;

@WebServlet("/etudiant/edit")
public class EtudiantEditServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            EtudiantServiceRemote etudiantService = EJBClientUtil.lookupEJB(
                "EtudiantService", 
                "services.EtudiantServiceRemote"
            );
            
            Etudiant etudiant = etudiantService.trouverEtudiantParId(id);
            req.setAttribute("etudiant", etudiant);
            req.getRequestDispatcher("/etudiant/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            EtudiantServiceRemote etudiantService = EJBClientUtil.lookupEJB(
                "EtudiantService", 
                "services.EtudiantServiceRemote"
            );
            
            Etudiant etudiant = etudiantService.trouverEtudiantParId(id);
            etudiant.setNom(req.getParameter("nom"));
            etudiant.setPrenom(req.getParameter("prenom"));
            etudiant.setCne(req.getParameter("cne"));
            etudiant.setAdresse(req.getParameter("adresse"));
            etudiant.setNiveau(req.getParameter("niveau"));
            
            etudiantService.modifierEtudiant(etudiant);
            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

