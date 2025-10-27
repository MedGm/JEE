package services;

import entities.Module;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ModuleServiceRemote {
    Module ajouterModule(Module module);
    Module modifierModule(Module module);
    List<Module> listerModules();
    Module trouverModuleParId(Long id);
    void supprimerModule(Long id);
}

