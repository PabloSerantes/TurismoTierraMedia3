package model;

import java.util.HashMap;
import java.util.Objects;

import persistence.commons.DAOFactory;

public class Ofertable implements Comparable<Ofertable>{
    private Integer id;
    private String nombre;
    private Integer precio;
    private Double tiempo;
    private String conformacion;
    private Integer cupo;
    private int tipo=-1;
    private String tipoPromocion;
    private String especial;
    private boolean active;

    public boolean isValid() {
        return validate().isEmpty();
    }

    public HashMap<String, String> validate(){
        HashMap<String, String> errors = new HashMap<String, String>();
        if(this.nombre.isBlank()) errors.put("nombre", "El nombre es requerido");
        if(this.precio <= 0) errors.put("precio", "El precio debe ser positivo");
		if(this.cupo <= 0) errors.put("cupo", "El cupo debe ser positivo");
        if(this.tiempo <= 0) errors.put("tiempo", "El tiempo debe ser positivo");
        if(this.tipo<=0) errors.put("tipo", "El tipo es requerido");
        if((this.conformacion.isBlank()&&this.tipoPromocion.length()>0)
        ||(this.conformacion.length()>0&&this.tipoPromocion.isBlank())
        ){
            errors.put("PromocionError", "Las promociones requieren conformacion y tipodepromocion");
        }
        if(!(this.conformacion.isBlank()&&this.tipoPromocion.isBlank()) ){
            if(this.especial.isBlank()){
                errors.put("PromocionError", "Las promociones requieren su promocion especial");
            }
            switch(this.tipoPromocion){
                case "Porcentuales":
                        if( !( isInteger(this.especial) ) ){
                            errors.put("PromocionPorcentualError", "Valor invalido para descuento");
                        } else {
                            if(Integer.parseInt(this.especial)<=0 || Integer.parseInt(this.especial)>=100){
                                errors.put("PromocionPorcentualError", "Los descuentos no pueden ser menores o iguales que 0 ni mayores o iguales a 100");
                            }
                        }
                        break;
                case "Absolutas":
                        if( !( isInteger(this.especial) ) ){
                            errors.put("PromocionAbsolutaError", "Valor invalido para descuento");
                        } else {
                            if(Integer.parseInt(this.especial)<=0||Integer.parseInt(this.especial)>=this.precio){
                                errors.put("PromocionAbsolutaError", "Los descuentos tienen que ser mayores que 0 y menores que el precio de la atraccion");
                            }
                        }
                        break;
                case "AxB":
                        if(Objects.isNull(DAOFactory.getOfertableDAO().find(this.especial))){
                            errors.put("PromocionAxBError", "La atraccion de regalo es invalida");
                        }
                        break;
                default:
                    errors.put("PromocionError", "Tipo de promocion invalido");
                    break;
            }
        }
        return errors;
    }

    public boolean puedeOfertarse() {
        return cupo > 0;
    }

    public void venderse(){
        cupo -=1;
    }


    public Ofertable(Integer id, String nombre, Integer precio, Double tiempo, Integer cupo, int tipo, boolean active) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tiempo = tiempo;
        this.cupo = cupo;
        this.tipo = tipo;
        this.active = active;
    }


    public Ofertable(Integer id, String nombre, String conformacion, int tipo, String tipoPromocion, String especial, boolean active) {
        this.id = id;
        this.nombre = nombre;
        this.conformacion = conformacion;
        this.tipo = tipo;
        this.tipoPromocion = tipoPromocion;
        this.especial = especial;
        this.active = active;
        this.promGenerarDatos();
    }


    public Ofertable(String nombre, String conformacion, int tipo, String tipoPromocion, String especial) {
        this.nombre = nombre;
        this.conformacion = conformacion;
        this.tipo = tipo;
        this.tipoPromocion = tipoPromocion;
        this.especial = especial;
        this.active = true;
        this.promGenerarDatos();
    }

    public Ofertable(String nombre, Integer precio, Double tiempo, Integer cupo, int tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tiempo = tiempo;
        this.cupo = cupo;
        this.tipo = tipo;
    }
    

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return this.precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Double getTiempo() {
        return this.tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }

    public String getConformacion() {
        return this.conformacion;
    }

    public void setConformacion(String conformacion) {
        this.conformacion = conformacion;
    }

    public Integer getCupo() {
        return this.cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public int getTipo() {
        return this.tipo;
    }

    public String getStipo() {
    	switch(this.tipo) {
    	case 1:
    		return "Aventura";
    	case 2:
    		return "Paisaje";
    	case 3:
    		return "Degustacion";
    	default:
    		return "ayuda no se como llegue aca";
    	}
    }
    
    public boolean contiene(String oferta) {
    	if(this.nombre.equals(oferta)) {
    		return true;
    	} else {
    		if(this.esPromocion()) {
	    		String[] atracciones = this.conformacion.split(" - ");
	    		for (String atr : atracciones) {
					if(atr.equals(oferta)){
						return true;
					}
				}
	    		if(this.tipoPromocion.equals("AxB")) {
	    			return this.especial.equals(oferta);
	    		}
    		}
    	}
    	return false;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getEspecial() {
        return this.especial;
    }

    public void setEspecial(String especial) {
        this.especial = especial;
    }
    public String getTipoPromocion() {
        return this.tipoPromocion;
    }

    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private void promGenerarDatos(){
        this.precio = 0;
        this.tiempo = 0.0;
        this.cupo = Integer.MAX_VALUE;
        String[] atracciones = this.conformacion.split(" - ");
        for (String atr : atracciones) {
            this.precio += DAOFactory.getOfertableDAO().find(atr).getPrecio();
            this.tiempo += DAOFactory.getOfertableDAO().find(atr).getTiempo();
            if(DAOFactory.getOfertableDAO().find(atr).getCupo()<this.cupo){
                this.cupo = DAOFactory.getOfertableDAO().find(atr).getCupo();
            }
        }
        switch(this.tipoPromocion){
            case "Porcentuales":
                this.precio -= (this.precio/100)*(Integer.parseInt(this.especial));
                break;
            case "Absolutas":
                this.precio -= Integer.parseInt(this.especial);
                break;
            case "AxB":
                this.tiempo += DAOFactory.getOfertableDAO().find(especial).getTiempo();
                break;
        }
    }
    
    public boolean puedeComprar(String username) {
    	return DAOFactory.getUsuarioDAO().findByUsername(username).puedeComprar(this);
    }
    
    public boolean esPromocion() {
    	return !(this.conformacion.isBlank());
    }

    @Override
    public int compareTo(Ofertable o) {
    	if(this.esPromocion() && !(o.esPromocion()) ) {
    		return 1;
    	}
    	if( !(this.esPromocion()) && o.esPromocion() ) {
    		return -1;
    	}
        int salida = Integer.compare(o.getPrecio(), this.getPrecio());
        if(salida!=0){
            return salida;
        }
        else{
            salida = Double.compare(o.getTiempo(), this.getTiempo());
        }
        return salida;
    }

    private boolean isInteger(String str) {
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    
    public boolean equals(Ofertable obj) {
    	if(this.id == obj.id||this.nombre.equals(obj.getNombre())) {
    		return true;
    	}
    	return false;
    }
}