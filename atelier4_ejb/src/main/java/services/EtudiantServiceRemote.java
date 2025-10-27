package services;

import entities.Etudiant;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface EtudiantServiceRemote {
    Etudiant ajouterEtudiant(Etudiant etudiant);
    Etudiant modifierEtudiant(Etudiant etudiant);
    List<Etudiant> listerEtudiants();
    Etudiant trouverEtudiantParId(Long id);
    void supprimerEtudiant(Long id);
}