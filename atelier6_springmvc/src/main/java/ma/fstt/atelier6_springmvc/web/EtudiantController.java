package ma.fstt.atelier6_springmvc.web;

import ma.fstt.atelier6_springmvc.model.Etudiant;
import ma.fstt.atelier6_springmvc.service.EtudiantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping
    public String list(@RequestParam(value = "q", required = false) String q, Model model) {
        if (q != null && !q.isBlank()) {
            model.addAttribute("etudiants", etudiantService.searchByNom(q));
        } else {
            model.addAttribute("etudiants", etudiantService.findAll());
        }
        return "etudiants/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "etudiants/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Etudiant etudiant, BindingResult br) {
        if (br.hasErrors()) {
            return "etudiants/form";
        }
        etudiantService.save(etudiant);
        return "redirect:/etudiants";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        var e = etudiantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("etudiant", e);
        return "etudiants/form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        etudiantService.deleteById(id);
        return "redirect:/etudiants";
    }
}

