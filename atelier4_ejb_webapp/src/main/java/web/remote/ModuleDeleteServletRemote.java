package web.remote;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ModuleServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;

@WebServlet("/module/delete")
public class ModuleDeleteServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            ModuleServiceRemote moduleService = EJBClientUtil.lookupEJB(
                "ModuleService", 
                "services.ModuleServiceRemote"
            );
            
            moduleService.supprimerModule(id);
            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

