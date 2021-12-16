package persistence;

import java.sql.SQLException;

import model.TokenSesion;
import persistence.commons.GenericDAO;

public interface TokenSesionDAO extends GenericDAO<TokenSesion>{
	public abstract boolean exist(String nombre) throws SQLException;
	public abstract boolean isValid(String nombre) throws SQLException;
	public abstract TokenSesion findByUsername(String Username);
	public abstract int delete(String username);
}
