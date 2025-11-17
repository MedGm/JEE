package ma.fstt.atelier6_springmvc.service;

import ma.fstt.atelier6_springmvc.model.Absence;
import ma.fstt.atelier6_springmvc.model.Etudiant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbsenceService {
    List<Absence> findAll();
    Optional<Absence> findById(Long id);
    Absence save(Absence absence);
    Absence update(Long id, Absence absence);
    void deleteById(Long id);
    List<Absence> findByEtudiant(Etudiant etudiant);
    List<Absence> findByDateRange(LocalDate start, LocalDate end);
    List<Absence> findByJustifie(boolean justifie);
}

