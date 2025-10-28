package web.remote;

import entities.Module;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ModuleServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;

@WebServlet("/module/add")
public class ModuleAddServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/module/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            ModuleServiceRemote moduleService = EJBClientUtil.lookupEJB(
                "ModuleService", 
                "services.ModuleServiceRemote"
            );
            
            Module m = new Module();
            m.setNomModule(req.getParameter("nomModule"));
            moduleService.ajouterModule(m);
            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

