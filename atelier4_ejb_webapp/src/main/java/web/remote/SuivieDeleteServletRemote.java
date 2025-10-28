package web.remote;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.SuivieServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;

@WebServlet("/suivie/delete")
public class SuivieDeleteServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            SuivieServiceRemote suivieService = EJBClientUtil.lookupEJB(
                "SuivieService", 
                "services.SuivieServiceRemote"
            );
            
            suivieService.supprimerSuivie(id);
            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

