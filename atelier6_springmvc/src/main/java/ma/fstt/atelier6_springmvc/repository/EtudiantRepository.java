package ma.fstt.atelier6_springmvc.repository;

import ma.fstt.atelier6_springmvc.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    List<Etudiant> findByNomContainingIgnoreCase(String nom);
}

