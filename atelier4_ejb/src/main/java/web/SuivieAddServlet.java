package web;

import entities.Etudiant;
import entities.Module;
import entities.Suivie;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EtudiantServiceRemote;
import services.ModuleServiceRemote;
import services.SuivieServiceRemote;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/suivie/add")
public class SuivieAddServlet extends HttpServlet {
    @EJB
    private SuivieServiceRemote suivieService;
    
    @EJB
    private EtudiantServiceRemote etudiantService;
    
    @EJB
    private ModuleServiceRemote moduleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("etudiants", etudiantService.listerEtudiants());
        req.setAttribute("modules", moduleService.listerModules());
        req.getRequestDispatcher("/suivie/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Suivie s = new Suivie();
        s.setNote(Double.parseDouble(req.getParameter("note")));
        s.setDate(LocalDate.parse(req.getParameter("date")));
        
        Long etudiantId = Long.parseLong(req.getParameter("idEtudiant"));
        Long moduleId = Long.parseLong(req.getParameter("idModule"));
        
        Etudiant etudiant = etudiantService.trouverEtudiantParId(etudiantId);
        Module module = moduleService.trouverModuleParId(moduleId);
        
        s.setEtudiant(etudiant);
        s.setModule(module);
        
        suivieService.ajouterSuivie(s);
        resp.sendRedirect("list");
    }
}

