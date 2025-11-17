package ma.fstt.atelier6_springmvc.repository;

import ma.fstt.atelier6_springmvc.model.Absence;
import ma.fstt.atelier6_springmvc.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEtudiant(Etudiant etudiant);
    List<Absence> findByDateAbsenceBetween(LocalDate start, LocalDate end);
    List<Absence> findByJustifie(boolean justifie);
}

