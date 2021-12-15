package model;

import java.util.ArrayList;
import java.util.List;

public class OfertasUsuario {
	public int precio;
	public String tipo;
	public String nombre;
	
	
	public OfertasUsuario(Ofertable oferta) {
		List<String> tipos = new ArrayList<String>();
		tipos.add("placeholder");
		tipos.add("Aventura");
		tipos.add("Paisaje");
		tipos.add("Degustacion");
		this.precio = oferta.getPrecio();
		this.tipo = tipos.get(oferta.getTipo());
		this.nombre = oferta.getNombre();
	}
	
	public int precio(){
		return this.precio();
	}
}
