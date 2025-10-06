package ma.fstt.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.fstt.dao.ClientDAO;
import ma.fstt.entities.Client;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ClientService implements ClientInterface {

    private ClientDAO clientDAO;

    @Inject
    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public ClientService() {
    }

    @Override
    public void create(Client client) throws SQLException {
        clientDAO.create(client);
    }

    @Override
    public void update(Client client) throws SQLException {
        clientDAO.update(client);
    }

    @Override
    public void delete(Client client) throws SQLException {
        clientDAO.delete(client);
    }

    @Override
    public Client findOne(Client client) throws SQLException {
        return clientDAO.findOne(client);
    }

    @Override
    public List<Client> findAll() throws SQLException {
        return clientDAO.findAll();
    }
}
