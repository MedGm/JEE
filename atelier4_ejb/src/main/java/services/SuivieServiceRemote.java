package services;

import entities.Suivie;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface SuivieServiceRemote {
    Suivie ajouterSuivie(Suivie suivie);
    Suivie modifierSuivie(Suivie suivie);
    List<Suivie> listerSuivies();
    Suivie trouverSuivieParId(Long id);
    void supprimerSuivie(Long id);
}

