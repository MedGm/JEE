package web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EtudiantServiceRemote;

import java.io.IOException;

@WebServlet("/etudiant/list")
public class EtudiantListServlet extends HttpServlet {
    @EJB
    private EtudiantServiceRemote etudiantService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("etudiants", etudiantService.listerEtudiants());
        req.getRequestDispatcher("/etudiant/list.jsp").forward(req, resp);
    }
}