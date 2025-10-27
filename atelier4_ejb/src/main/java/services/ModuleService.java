package services;

import entities.Module;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ModuleService implements ModuleServiceRemote {

    @PersistenceContext(unitName = "cnx")
    private EntityManager em;

    @Override
    public Module ajouterModule(Module module) {
        em.persist(module);
        return module;
    }

    @Override
    public Module modifierModule(Module module) {
        return em.merge(module);
    }

    @Override
    public List<Module> listerModules() {
        return em.createQuery("SELECT m FROM Module m", Module.class).getResultList();
    }

    @Override
    public Module trouverModuleParId(Long id) {
        return em.find(Module.class, id);
    }

    @Override
    public void supprimerModule(Long id) {
        Module m = em.find(Module.class, id);
        if (m != null) {
            em.remove(m);
        }
    }
}

