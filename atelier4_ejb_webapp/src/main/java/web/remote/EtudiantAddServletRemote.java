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

/**
 * Remote EJB version - uses JNDI lookup for Tomcat deployment
 */
@WebServlet("/etudiant/add")
public class EtudiantAddServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/etudiant/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            EtudiantServiceRemote etudiantService = EJBClientUtil.lookupEJB(
                "EtudiantService", 
                "services.EtudiantServiceRemote"
            );
            
            Etudiant e = new Etudiant();
            e.setNom(req.getParameter("nom"));
            e.setPrenom(req.getParameter("prenom"));
            e.setCne(req.getParameter("cne"));
            e.setAdresse(req.getParameter("adresse"));
            e.setNiveau(req.getParameter("niveau"));
            
            etudiantService.ajouterEtudiant(e);
            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

