package ma.fstt.service;

import ma.fstt.entities.Produit;
import java.sql.SQLException;
import java.util.List;

public interface ProduitInterface {
	void create(Produit produit) throws SQLException;
	void update(Produit produit) throws SQLException;
	void delete(Produit produit) throws SQLException;
	Produit findOne(Produit produit) throws SQLException;
	List<Produit> findAll() throws SQLException;
}
