package web.remote;

import entities.Etudiant;
import entities.Module;
import entities.Suivie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EtudiantServiceRemote;
import services.ModuleServiceRemote;
import services.SuivieServiceRemote;
import web.EJBClientUtil;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/suivie/add")
public class SuivieAddServletRemote extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            EtudiantServiceRemote etudiantService = EJBClientUtil.lookupEJB(
                "EtudiantService", "services.EtudiantServiceRemote"
            );
            ModuleServiceRemote moduleService = EJBClientUtil.lookupEJB(
                "ModuleService", "services.ModuleServiceRemote"
            );
            
            req.setAttribute("etudiants", etudiantService.listerEtudiants());
            req.setAttribute("modules", moduleService.listerModules());
            req.getRequestDispatcher("/suivie/add.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            SuivieServiceRemote suivieService = EJBClientUtil.lookupEJB(
                "SuivieService", "services.SuivieServiceRemote"
            );
            EtudiantServiceRemote etudiantService = EJBClientUtil.lookupEJB(
                "EtudiantService", "services.EtudiantServiceRemote"
            );
            ModuleServiceRemote moduleService = EJBClientUtil.lookupEJB(
                "ModuleService", "services.ModuleServiceRemote"
            );
            
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
        } catch (Exception e) {
            throw new ServletException("Error accessing remote EJB", e);
        }
    }
}

