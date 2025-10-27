package web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.SuivieServiceRemote;

import java.io.IOException;

@WebServlet("/suivie/list")
public class SuivieListServlet extends HttpServlet {
    @EJB
    private SuivieServiceRemote suivieService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("suivies", suivieService.listerSuivies());
        req.getRequestDispatcher("/suivie/list.jsp").forward(req, resp);
    }
}

