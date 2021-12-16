package model;

import persistence.commons.DAOFactory;

public class Itinerarios {
    private int id;
    private String usuario;
    private String atracciones;
    private int promociones;
    private int precio;
    private Double tiempo;

    public Itinerarios(int id, String usuario, String atracciones, int promociones, int precio, Double tiempo) {
        this.id = id;
        this.usuario = usuario;
        this.atracciones = atracciones;
        this.promociones = promociones;
        this.precio = precio;
        this.tiempo = tiempo;
    }
    
    public Itinerarios(String usuario, String atracciones, int precio, Double tiempo) {
        this.usuario = usuario;
        this.atracciones = atracciones;
        this.precio = precio;
        this.tiempo = tiempo;
    }

    public Itinerarios(String usuario, int promociones) {
        this.usuario = usuario;
        this.promociones = promociones;
        this.obtenerDatos();
    }

    private void obtenerDatos(){
        this.precio = 0;
        this.tiempo = 0.0;
        Ofertable promocion = DAOFactory.getOfertableDAO().find(this.id);
        this.tiempo = promocion.getTiempo();
        this.precio = promocion.getPrecio();
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAtracciones() {
        return this.atracciones;
    }

    public void setAtracciones(String atracciones) {
        this.atracciones = atracciones;
    }

    public int getPromociones() {
        return this.promociones;
    }

    public void setPromociones(int promociones) {
        this.promociones = promociones;
    }

    public int getPrecio() {
        return this.precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Double getTiempo() {
        return this.tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }
    
    public boolean esPromocion() {
    	return this.atracciones.isBlank();
    }

}
