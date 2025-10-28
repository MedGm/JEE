package web.remote;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EtudiantServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;

@WebServlet("/etudiant/delete")
public class EtudiantDeleteServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            EtudiantServiceRemote etudiantService = EJBClientUtil.lookupEJB(
                "EtudiantService", 
                "services.EtudiantServiceRemote"
            );
            
            etudiantService.supprimerEtudiant(id);
            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

