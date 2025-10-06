package ma.fstt.service;

import ma.fstt.entities.Commande;
import java.sql.SQLException;
import java.util.List;

public interface CommandeInterface {
	void create(Commande commande) throws SQLException;
	void update(Commande commande) throws SQLException;
	void delete(Commande commande) throws SQLException;
	Commande findOne(Commande commande) throws SQLException;
	List<Commande> findAll() throws SQLException;
}
