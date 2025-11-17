package ma.fstt.atelier6_springmvc.web;

import ma.fstt.atelier6_springmvc.model.Absence;
import ma.fstt.atelier6_springmvc.model.Etudiant;
import ma.fstt.atelier6_springmvc.service.AbsenceService;
import ma.fstt.atelier6_springmvc.service.EtudiantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;

@Controller
@RequestMapping("/absences")
public class AbsenceController {

    private final AbsenceService absenceService;
    private final EtudiantService etudiantService;

    public AbsenceController(AbsenceService absenceService, EtudiantService etudiantService) {
        this.absenceService = absenceService;
        this.etudiantService = etudiantService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("absences", absenceService.findAll());
        return "absences/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("absence", new Absence());
        model.addAttribute("etudiants", etudiantService.findAll());
        return "absences/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Absence absence, BindingResult br,
                      @RequestParam("etudiantId") Long etudiantId, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("etudiants", etudiantService.findAll());
            return "absences/form";
        }
        // Si c'est une nouvelle absence ou si l'étudiant change
        if (absence.getId() == null || absence.getEtudiant() == null || 
            !absence.getEtudiant().getId().equals(etudiantId)) {
            Etudiant etu = etudiantService.findById(etudiantId)
                    .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable: " + etudiantId));
            absence.setEtudiant(etu);
        }
        absenceService.save(absence);
        return "redirect:/absences";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        var a = absenceService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("absence", a);
        model.addAttribute("etudiants", etudiantService.findAll());
        return "absences/form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        absenceService.deleteById(id);
        return "redirect:/absences";
    }

    // Exemple d'une recherche par période
    @GetMapping("/search")
    public String searchByDateRange(@RequestParam("from") String from,
                                    @RequestParam("to") String to,
                                    Model model) {
        LocalDate start = LocalDate.parse(from);
        LocalDate end = LocalDate.parse(to);
        model.addAttribute("absences", absenceService.findByDateRange(start, end));
        return "absences/list";
    }
}

