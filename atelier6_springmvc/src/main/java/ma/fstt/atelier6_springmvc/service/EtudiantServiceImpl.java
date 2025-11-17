package ma.fstt.atelier6_springmvc.service;

import ma.fstt.atelier6_springmvc.model.Etudiant;
import ma.fstt.atelier6_springmvc.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository repo;

    @Autowired
    public EtudiantServiceImpl(EtudiantRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Etudiant> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Etudiant> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Etudiant save(Etudiant e) {
        return repo.save(e);
    }

    @Override
    public Etudiant update(Long id, Etudiant e) {
        return repo.findById(id).map(existing -> {
            existing.setNom(e.getNom());
            existing.setPrenom(e.getPrenom());
            existing.setDateNaissance(e.getDateNaissance());
            existing.setClasse(e.getClasse());
            existing.setEmail(e.getEmail());
            existing.setTelephone(e.getTelephone());
            return repo.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Etudiant> searchByNom(String nom) {
        return repo.findByNomContainingIgnoreCase(nom);
    }
}

