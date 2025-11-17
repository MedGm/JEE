package ma.fstt.atelier6_springmvc.service;

import ma.fstt.atelier6_springmvc.model.Absence;
import ma.fstt.atelier6_springmvc.model.Etudiant;
import ma.fstt.atelier6_springmvc.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AbsenceServiceImpl implements AbsenceService {

    private final AbsenceRepository repo;

    @Autowired
    public AbsenceServiceImpl(AbsenceRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Absence> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Absence> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Absence save(Absence a) {
        return repo.save(a);
    }

    @Override
    public Absence update(Long id, Absence a) {
        return repo.findById(id).map(existing -> {
            existing.setDateAbsence(a.getDateAbsence());
            existing.setMotif(a.getMotif());
            existing.setJustifie(a.isJustifie());
            existing.setRemarque(a.getRemarque());
            // Ne change pas l'étudiant ici par défaut
            return repo.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Absence introuvable: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Absence> findByEtudiant(Etudiant e) {
        return repo.findByEtudiant(e);
    }

    @Override
    public List<Absence> findByDateRange(LocalDate start, LocalDate end) {
        return repo.findByDateAbsenceBetween(start, end);
    }

    @Override
    public List<Absence> findByJustifie(boolean justifie) {
        return repo.findByJustifie(justifie);
    }
}

