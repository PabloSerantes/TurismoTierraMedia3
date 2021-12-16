package service;

import persistence.commons.DAOFactory;
import java.util.List;
import java.util.Objects;

import model.Usuario;

public class UsuariosService {
	public List<Usuario> findAll(){
		return DAOFactory.getUsuarioDAO().findAll();
	}
	
	public Usuario find(String username) {
		return DAOFactory.getUsuarioDAO().findByUsername(username);
	}
	
	public int insert(String nombre, int preferencia, int presupuesto, Double tiempoDisponible,Boolean admin,String password) {
		Usuario nuevo = new Usuario(nombre, password, admin, presupuesto, preferencia, tiempoDisponible);
		if( nuevo.isValid() && Objects.isNull(DAOFactory.getUsuarioDAO().findByUsername(nombre)) ) {
			int salida = DAOFactory.getUsuarioDAO().insert(nuevo);
			return salida;
		} else {
			return -1;
		}
	}
	
	public boolean delete(String username) {
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		if(Objects.nonNull(user)) {
			int salida = DAOFactory.getUsuarioDAO().delete(user.getId());
			return salida == 1;
		} else {
			return false;
		}
	}
}
