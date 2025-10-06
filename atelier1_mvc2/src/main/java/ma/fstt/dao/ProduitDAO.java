package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Produit;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ProduitDAO extends DAO<Produit> {

    public ProduitDAO() {}

    @Override
    public void create(Produit entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("INSERT INTO Produit (nom, prix, stock) VALUES (?, ?, ?)")) {
            ps.setString(1, entity.getNom());
            ps.setDouble(2, entity.getPrix());
            ps.setInt(3, entity.getStock());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Produit entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("UPDATE Produit SET nom = ?, prix = ?, stock = ? WHERE id = ?")) {
            ps.setString(1, entity.getNom());
            ps.setDouble(2, entity.getPrix());
            ps.setInt(3, entity.getStock());
            ps.setLong(4, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Produit entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("DELETE FROM Produit WHERE id = ?")) {
            ps.setLong(1, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public Produit findOne(Produit entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement("SELECT * FROM Produit WHERE id = ?")) {
            ps.setLong(1, entity.getId());
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produit p = new Produit();
                    p.setId(rs.getLong("id"));
                    p.setNom(rs.getString("nom"));
                    p.setPrix(rs.getDouble("prix"));
                    p.setStock(rs.getInt("stock"));
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public List<Produit> findAll() throws SQLException {
        java.util.List<Produit> produits = new java.util.ArrayList<>();
        try (java.sql.Statement st = connection.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT * FROM Produit")) {
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getLong("id"));
                p.setNom(rs.getString("nom"));
                p.setPrix(rs.getDouble("prix"));
                p.setStock(rs.getInt("stock"));
                produits.add(p);
            }
        }
        return produits;
    }
}
