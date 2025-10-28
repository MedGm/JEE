package web.remote;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.SuivieServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;

@WebServlet("/suivie/list")
public class SuivieListServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            SuivieServiceRemote suivieService = EJBClientUtil.lookupEJB(
                "SuivieService", 
                "services.SuivieServiceRemote"
            );
            
            req.setAttribute("suivies", suivieService.listerSuivies());
            req.getRequestDispatcher("/suivie/list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

