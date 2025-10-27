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

@WebServlet("/module/add")
public class ModuleAddServlet extends HttpServlet {
    @EJB
    private ModuleServiceRemote moduleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/module/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Module m = new Module();
        m.setNomModule(req.getParameter("nomModule"));
        moduleService.ajouterModule(m);
        resp.sendRedirect("list");
    }
}

