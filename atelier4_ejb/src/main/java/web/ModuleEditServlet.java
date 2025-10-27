package web;

import entities.Module;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ModuleServiceRemote;

import java.io.IOException;

@WebServlet("/module/edit")
public class ModuleEditServlet extends HttpServlet {
    @EJB
    private ModuleServiceRemote moduleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Module module = moduleService.trouverModuleParId(id);
        req.setAttribute("module", module);
        req.getRequestDispatcher("/module/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Module module = moduleService.trouverModuleParId(id);
        
        module.setNomModule(req.getParameter("nomModule"));
        
        moduleService.modifierModule(module);
        resp.sendRedirect("list");
    }
}

