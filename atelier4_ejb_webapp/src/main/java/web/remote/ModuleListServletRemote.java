package web.remote;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ModuleServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;

@WebServlet("/module/list")
public class ModuleListServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            ModuleServiceRemote moduleService = EJBClientUtil.lookupEJB(
                "ModuleService", 
                "services.ModuleServiceRemote"
            );
            
            req.setAttribute("modules", moduleService.listerModules());
            req.getRequestDispatcher("/module/list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

