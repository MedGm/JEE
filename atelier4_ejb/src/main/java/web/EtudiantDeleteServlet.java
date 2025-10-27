package web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EtudiantServiceRemote;

import java.io.IOException;

@WebServlet("/etudiant/delete")
public class EtudiantDeleteServlet extends HttpServlet {
    @EJB
    private EtudiantServiceRemote etudiantService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        etudiantService.supprimerEtudiant(id);
        resp.sendRedirect("list");
    }
}

