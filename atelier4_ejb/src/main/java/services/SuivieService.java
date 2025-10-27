package services;

import entities.Suivie;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class SuivieService implements SuivieServiceRemote {

    @PersistenceContext(unitName = "cnx")
    private EntityManager em;

    @Override
    public Suivie ajouterSuivie(Suivie suivie) {
        em.persist(suivie);
        return suivie;
    }

    @Override
    public Suivie modifierSuivie(Suivie suivie) {
        return em.merge(suivie);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Suivie> listerSuivies() {
        // Use DISTINCT to avoid duplicate rows from JOIN
        String jpql = "SELECT DISTINCT s FROM Suivie s "
                + "LEFT JOIN FETCH s.etudiant "
                + "LEFT JOIN FETCH s.module";
        List<Suivie> suivies = em.createQuery(jpql, Suivie.class).getResultList();
        return suivies;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Suivie trouverSuivieParId(Long id) {
        try {
            return em.createQuery("SELECT s FROM Suivie s WHERE s.idSuivie = :id", Suivie.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void supprimerSuivie(Long id) {
        Suivie s = em.find(Suivie.class, id);
        if (s != null) {
            em.remove(s);
        }
    }
}

