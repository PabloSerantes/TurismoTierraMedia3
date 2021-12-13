package model;

import java.util.HashMap;

import persistence.commons.DAOFactory;

public class Ofertable {
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
        this.cupo = 0;
        String[] atracciones = this.conformacion.split(" - ");
        for (String atr : atracciones) {
            this.precio += DAOFactory.getOfertableDAO().find(atr).getPrecio();
            this.tiempo += DAOFactory.getOfertableDAO().find(atr).getTiempo();
            this.cupo += DAOFactory.getOfertableDAO().find(atr).getCupo();
        }
    }
}
