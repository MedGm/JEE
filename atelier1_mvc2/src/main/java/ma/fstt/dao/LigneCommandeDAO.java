package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.LigneDeCommande;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class LigneCommandeDAO extends DAO<LigneDeCommande> {
    @Override
    public void create(LigneDeCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("INSERT INTO LigneDeCommande (commande_id, produit_id, quantite, prix_unitaire) VALUES (?, ?, ?, ?)")) {
            ps.setLong(1, entity.getCommandeId());
            ps.setLong(2, entity.getProduitId());
            ps.setInt(3, entity.getQuantite());
            ps.setDouble(4, entity.getPrixUnitaire());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(LigneDeCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("UPDATE LigneDeCommande SET commande_id = ?, produit_id = ?, quantite = ?, prix_unitaire = ? WHERE id = ?")) {
            ps.setLong(1, entity.getCommandeId());
            ps.setLong(2, entity.getProduitId());
            ps.setInt(3, entity.getQuantite());
            ps.setDouble(4, entity.getPrixUnitaire());
            ps.setLong(5, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(LigneDeCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("DELETE FROM LigneDeCommande WHERE id = ?")) {
            ps.setLong(1, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public LigneDeCommande findOne(LigneDeCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("SELECT * FROM LigneDeCommande WHERE id = ?")) {
            ps.setLong(1, entity.getId());
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LigneDeCommande l = new LigneDeCommande();
                    l.setId(rs.getLong("id"));
                    l.setCommandeId(rs.getLong("commande_id"));
                    l.setProduitId(rs.getLong("produit_id"));
                    l.setQuantite(rs.getInt("quantite"));
                    l.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                    return l;
                }
            }
        }
        return null;
    }

    @Override
    public List<LigneDeCommande> findAll() throws SQLException {
        java.util.List<LigneDeCommande> lignes = new java.util.ArrayList<>();

        try (java.sql.Statement st = connection.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT * FROM LigneDeCommande")) {
            while (rs.next()) {
                LigneDeCommande l = new LigneDeCommande();
                l.setId(rs.getLong("id"));
                l.setCommandeId(rs.getLong("commande_id"));
                l.setProduitId(rs.getLong("produit_id"));
                l.setQuantite(rs.getInt("quantite"));
                l.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                lignes.add(l);
            }
        }
        return lignes;
    }

    public java.util.List<LigneDeCommande> findByCommandeId(long commandeId) throws SQLException {
        java.util.List<LigneDeCommande> lignes = new java.util.ArrayList<>();
        try (java.sql.PreparedStatement ps = connection.prepareStatement("SELECT * FROM LigneDeCommande WHERE commande_id = ?")) {
            ps.setLong(1, commandeId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LigneDeCommande l = new LigneDeCommande();
                    l.setId(rs.getLong("id"));
                    l.setCommandeId(rs.getLong("commande_id"));
                    l.setProduitId(rs.getLong("produit_id"));
                    l.setQuantite(rs.getInt("quantite"));
                    l.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                    lignes.add(l);
                }
            }
        }
        return lignes;
    }

    public double sumTotalByCommandeId(long commandeId) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "SELECT COALESCE(SUM(quantite * prix_unitaire), 0) AS total FROM LigneDeCommande WHERE commande_id = ?")) {
            ps.setLong(1, commandeId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }
}
