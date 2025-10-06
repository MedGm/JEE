package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Commande;
import ma.fstt.entities.Client;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class CommandeDAO extends DAO<Commande> {

    public CommandeDAO() {}

    @Override
    public void create(Commande entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO Commande (date_commande, client_id, total) VALUES (?, ?, ?)")) {
            ps.setString(1, entity.getDate_commande());
            ps.setLong(2, entity.getClient_id().getClient_id());
            ps.setDouble(3, entity.getTotal());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Commande entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE Commande SET date_commande = ?, client_id = ?, total = ? WHERE id = ?")) {
            ps.setString(1, entity.getDate_commande());
            ps.setLong(2, entity.getClient_id().getClient_id());
            ps.setDouble(3, entity.getTotal());
            ps.setLong(4, entity.getCommande_id());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Commande entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Commande WHERE id = ?")) {
            ps.setLong(1, entity.getCommande_id());
            ps.executeUpdate();
        }
    }

    @Override
    public Commande findOne(Commande entity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Commande WHERE id = ?")) {
            ps.setLong(1, entity.getCommande_id());
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Commande c = new Commande();
                    c.setCommande_id(rs.getLong("id"));
                    c.setDate_commande(rs.getString("date_commande"));
                    // For client_id, you may want to fetch the Client entity separately
                    Client client = new Client();
                    client.setClient_id(rs.getLong("client_id"));
                    c.setClient_id(client);
                    c.setTotal(rs.getDouble("total"));
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public List<Commande> findAll() throws SQLException {
        List<Commande> commandes = new java.util.ArrayList<>();
        try (java.sql.Statement st = connection.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT * FROM Commande")) {
            while (rs.next()) {
                Commande c = new Commande();
                c.setCommande_id(rs.getLong("id"));
                c.setDate_commande(rs.getString("date_commande"));
                Client client = new Client();
                client.setClient_id(rs.getLong("client_id"));
                c.setClient_id(client);
                c.setTotal(rs.getDouble("total"));
                commandes.add(c);
            }
        }
        return commandes;
    }
}
