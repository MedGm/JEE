package ma.fstt.atelier6_springmvc.service;

import ma.fstt.atelier6_springmvc.model.Etudiant;
import java.util.List;
import java.util.Optional;

public interface EtudiantService {
    List<Etudiant> findAll();
    Optional<Etudiant> findById(Long id);
    Etudiant save(Etudiant etudiant);
    Etudiant update(Long id, Etudiant etudiant);
    void deleteById(Long id);
    List<Etudiant> searchByNom(String nom);
}

