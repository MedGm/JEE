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

@WebServlet("/module/edit")
public class ModuleEditServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            ModuleServiceRemote moduleService = EJBClientUtil.lookupEJB(
                "ModuleService", 
                "services.ModuleServiceRemote"
            );
            
            Module module = moduleService.trouverModuleParId(id);
            req.setAttribute("module", module);
            req.getRequestDispatcher("/module/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            ModuleServiceRemote moduleService = EJBClientUtil.lookupEJB(
                "ModuleService", 
                "services.ModuleServiceRemote"
            );
            
            Module module = moduleService.trouverModuleParId(id);
            module.setNomModule(req.getParameter("nomModule"));
            
            moduleService.modifierModule(module);
            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

