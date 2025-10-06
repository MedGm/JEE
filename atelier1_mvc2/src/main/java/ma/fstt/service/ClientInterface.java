package ma.fstt.service;

import ma.fstt.entities.Client;
import java.sql.SQLException;
import java.util.List;

public interface ClientInterface {
	void create(Client client) throws SQLException;
	void update(Client client) throws SQLException;
	void delete(Client client) throws SQLException;
	Client findOne(Client client) throws SQLException;
	List<Client> findAll() throws SQLException;
}
