package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientDAO extends DAO<Client> {

    public ClientDAO() {}

    @Override
    public void create(Client entity) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO `Client` (`nom`, `email`, `adresse`) VALUES (?, ?, ?)");
        ps.setString(1, entity.getNom());
        ps.setString(2, entity.getEmail());
        ps.setString(3, entity.getAdresse());
        ps.executeUpdate();
    }

    @Override
    public void update(Client entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE Client SET nom = ?, email = ?, adresse = ? WHERE id = ?")) {
            ps.setString(1, entity.getNom());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getAdresse());
            ps.setLong(4, entity.getClient_id());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Client entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Client WHERE id = ?")) {
            ps.setLong(1, entity.getClient_id());
            ps.executeUpdate();
        }
    }

    @Override
    public Client findOne(Client entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Client WHERE id = ?")) {
            ps.setLong(1, entity.getClient_id());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Client c = new Client();
                    c.setClient_id(rs.getLong("id"));
                    c.setNom(rs.getString("nom"));
                    c.setEmail(rs.getString("email"));
                    c.setAdresse(rs.getString("adresse"));
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Client")) {
            while (rs.next()) {
                Client c = new Client();
                c.setClient_id(rs.getLong("id"));
                c.setNom(rs.getString("nom"));
                c.setEmail(rs.getString("email"));
                c.setAdresse(rs.getString("adresse"));
                clients.add(c);
            }
        }
        return clients;
    }
}
