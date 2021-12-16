package service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Ofertable;
import persistence.commons.DAOFactory;

public class OfertasService {
    public List<Ofertable> list(){
        return DAOFactory.getOfertableDAO().findAll();
    }

    public List<Ofertable> list(int tipo){
        List<Ofertable> entrada = DAOFactory.getOfertableDAO().findAll();
        List<Ofertable> salida = new ArrayList<Ofertable>();
        List<Ofertable> distintos = new ArrayList<Ofertable>();
        for (Ofertable oferta : entrada) {
            if(oferta.getTipo() == tipo){
                salida.add(oferta);
            }else {
            	distintos.add(oferta);
            }
        }
        Collections.sort(distintos);
        Collections.sort(salida);
        salida.addAll(distintos);
        return salida;
    }

    public int update(Ofertable oferta){
        try{
            int salida = -1;
            if(oferta.isValid()){
                salida = DAOFactory.getOfertableDAO().update(oferta);
            }
            return salida;
        } catch(Exception e){
            //-1 simboliza error
            return -1;
        }
    }

    public Ofertable find(int id){
        return DAOFactory.getOfertableDAO().find(id);
    }

    //promocion
    public int insert(String nombre, String conformacion, int tipo, String tipoPromocion, String especial){
        try{
            int salida = -1;
            Ofertable nuevo = new Ofertable(nombre, conformacion, tipo, tipoPromocion, especial);
            if(nuevo.isValid()){
                salida = DAOFactory.getOfertableDAO().insert(nuevo);
            }
            return salida;
        } catch(Exception e){
            return -1;
        }
    }

    //atraccion
    public int insert(String nombre, Integer precio, Double tiempo, Integer cupo, int tipo){
        try{
            int salida = -1;
            Ofertable nuevo = new Ofertable(nombre, precio, tiempo, cupo, tipo);
            if(nuevo.isValid()){
                salida = DAOFactory.getOfertableDAO().insert(nuevo);
            }
            return salida;
        } catch(Exception e){
            return -1;
        }
    }
    
    public int delete(String nombre) {
    	return DAOFactory.getOfertableDAO().delete(nombre);
    }
}
