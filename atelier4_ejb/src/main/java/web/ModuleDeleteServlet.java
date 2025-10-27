package web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ModuleServiceRemote;

import java.io.IOException;

@WebServlet("/module/delete")
public class ModuleDeleteServlet extends HttpServlet {
    @EJB
    private ModuleServiceRemote moduleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        moduleService.supprimerModule(id);
        resp.sendRedirect("list");
    }
}

