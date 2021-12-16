package service;
import java.util.ArrayList;
import java.util.List;
import model.Itinerarios;
import model.Ofertable;
import model.Usuario;
import persistence.commons.DAOFactory;

public class ItinerariosService {
	public List<Itinerarios> listAll(){
		List<Itinerarios> salida = DAOFactory.getItinerariosDAO().findAll();
		return salida;
	}

    public Itinerarios find(int id){
        return DAOFactory.getItinerariosDAO().find(id);
    }
    
    public List<Itinerarios> findByUsername(String username) {
    	List<Itinerarios> general = DAOFactory.getItinerariosDAO().findAll();
    	List<Itinerarios> salida = new ArrayList<Itinerarios>();
    	for (Itinerarios itin : general) {
			if(itin.getUsuario().equals(username)) {
				salida.add(itin);
			}
		}
    	return salida;
    }

    public int insert(Ofertable oferta, String username){
        try{
        	Itinerarios nuevo;
        	Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
            int salida = -1;
            if(oferta.esPromocion()) {
            	nuevo = new Itinerarios(username, oferta.getId());
            	
            } else {
            	nuevo = new Itinerarios(username, oferta.getConformacion(), oferta.getPrecio(), oferta.getTiempo());
            }
            if(user.puedeComprar(oferta)) {
            	salida = DAOFactory.getItinerariosDAO().insert(nuevo);
            }
            if(salida!=-1) {
            	oferta.venderse();
            	user.comprar(oferta);
            	DAOFactory.getUsuarioDAO().update(user);
            	DAOFactory.getOfertableDAO().update(oferta);
            }
            return salida;
        } catch(Exception e){
            return -1;
        }
    }
}
