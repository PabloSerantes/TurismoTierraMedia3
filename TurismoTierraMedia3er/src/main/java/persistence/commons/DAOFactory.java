package persistence.commons;

import persistence.ItinerariosDAO;
import persistence.OfertableDAO;
import persistence.UsuarioDAO;
import persistence.impl.ItinerariosDAOImpl;
import persistence.impl.OfertableDAOImpl;
import persistence.impl.UsuarioDAOImpl;

public class DAOFactory {
	
	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOImpl();
	}
	
	public static ItinerariosDAO getItinerariosDAO() {
		return new ItinerariosDAOImpl();
	}

	public static OfertableDAO getOfertableDAO(){
		return new OfertableDAOImpl();
	}
}
