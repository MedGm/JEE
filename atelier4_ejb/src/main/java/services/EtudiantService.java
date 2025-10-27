package services;

import entities.Etudiant;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class EtudiantService implements EtudiantServiceRemote {

    @PersistenceContext(unitName = "cnx")
    private EntityManager em;

    @Override
    public Etudiant ajouterEtudiant(Etudiant etudiant) {
        em.persist(etudiant);
        return etudiant;
    }

    @Override
    public Etudiant modifierEtudiant(Etudiant etudiant) {
        return em.merge(etudiant);
    }

    @Override
    public List<Etudiant> listerEtudiants() {
        return em.createQuery("SELECT e FROM Etudiant e", Etudiant.class).getResultList();
    }

    @Override
    public Etudiant trouverEtudiantParId(Long id) {
        return em.find(Etudiant.class, id);
    }

    @Override
    public void supprimerEtudiant(Long id) {
        Etudiant e = em.find(Etudiant.class, id);
        if (e != null) {
            em.remove(e);
        }
    }
}