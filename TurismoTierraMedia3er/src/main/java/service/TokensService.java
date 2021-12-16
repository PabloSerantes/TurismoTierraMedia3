package service;

import java.util.List;

import model.TokenSesion;
import persistence.commons.DAOFactory;

public class TokensService {
	public boolean cleanSesions() {
		try {
			List<TokenSesion> tokens = DAOFactory.getTokenSesionDAO().findAll();
			for (TokenSesion tok : tokens) {
				DAOFactory.getTokenSesionDAO().delete(tok.getId());
			}
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean delete(String username) {
		return DAOFactory.getTokenSesionDAO().delete(username) != -1;
	}
	
	public boolean tokearUsuario(String username) {
		try {
			if(DAOFactory.getTokenSesionDAO().exist(username)) {
				TokenSesion t = DAOFactory.getTokenSesionDAO().findByUsername(username);
				return DAOFactory.getTokenSesionDAO().update(t) != -1;
			} else {
				TokenSesion nuevo = new TokenSesion(username);
				return DAOFactory.getTokenSesionDAO().insert(nuevo) != -1;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public String ObtenerToken(String username) {
		return DAOFactory.getTokenSesionDAO().findByUsername(username).getToken();
	}
	
	public boolean validar(String username, String Token) {
		try {
			if(DAOFactory.getTokenSesionDAO().exist(username)) {
				TokenSesion t = DAOFactory.getTokenSesionDAO().findByUsername(username);
				if(t.isValid()) {
					if(t.getUsername().equals(username) && t.getToken().equals(Token)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
