package web.remote;

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
@WebServlet("/etudiant/list")
public class EtudiantListServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            EtudiantServiceRemote etudiantService = EJBClientUtil.lookupEJB(
                "EtudiantService", 
                "services.EtudiantServiceRemote"
            );
            
            req.setAttribute("etudiants", etudiantService.listerEtudiants());
            req.getRequestDispatcher("/etudiant/list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

