package ma.fstt.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.fstt.dao.CommandeDAO;
import ma.fstt.entities.Commande;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class CommandeService implements CommandeInterface {


    private CommandeDAO commandeDAO;

    @Inject
    public CommandeService(CommandeDAO commandeDAO) {
        this.commandeDAO = commandeDAO;
    }

    public CommandeService() {
    }

    @Override
    public void create(Commande commande) throws SQLException {
        commandeDAO.create(commande);
    }

    @Override
    public void update(Commande commande) throws SQLException {
        commandeDAO.update(commande);
    }

    @Override
    public void delete(Commande commande) throws SQLException {
        commandeDAO.delete(commande);
    }

    @Override
    public Commande findOne(Commande commande) throws SQLException {
        return commandeDAO.findOne(commande);
    }

    @Override
    public List<Commande> findAll() throws SQLException {
        return commandeDAO.findAll();
    }
}
