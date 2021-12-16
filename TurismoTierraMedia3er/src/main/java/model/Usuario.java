package model;

import java.util.HashMap;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import persistence.commons.DAOFactory;

public class Usuario {
	private Integer id;
	private String username;
	private String password;
	private boolean admin;
	private	int preferencia;
	private int presupuesto;
	private Double tiempo;
	private boolean active;
	
	public boolean isValid() {
		return validate().isEmpty();
	}
	
	public HashMap<String, String> validate(){
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if(username.isBlank()) errors.put("name", "El nombre es requerido");	
		if(password.isBlank()) errors.put("password", "La contrase�a es requerida");
		else if(password.length() < 6) errors.put("password", "La contrase�a debe tener al menos 6 caracteres");
		if(presupuesto < 0) errors.put("price", "El dinero debe ser positivo");
		
		return errors;
	}	
	
	public boolean puedeComprar(Ofertable prod) {
		if(this.comprado(prod)) {
			return false;
		}
		return this.presupuesto >= prod.getPrecio() && this.tiempo >= prod.getTiempo() && prod.puedeOfertarse();
	}
	
	public boolean comprado(Ofertable oferta) {
		for(Itinerarios itn: new service.ItinerariosService().findByUsername(this.username)) {
			if(itn.esPromocion()) {
				Ofertable lectura = DAOFactory.getOfertableDAO().find( itn.getPromociones() );
				if(oferta.equals(lectura)) {
					return true;
				}
			} else {
				Ofertable lectura = DAOFactory.getOfertableDAO().find(itn.getAtracciones());
				if(oferta.equals(lectura)) {
					return true;
				}
			}
		}
		return false;
	}
	public void comprar(Ofertable prod) {
		presupuesto -= prod.getPrecio();
		tiempo -= prod.getTiempo();
	}
	
	public Usuario(Integer id, String username, String password, boolean admin, int presupuesto, int preferencia, Double tiempo, Boolean active) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.presupuesto = presupuesto;
		this.preferencia = preferencia;
		this.tiempo = tiempo;
		this.active = active;
	}
	
	public Usuario(String username, String password, boolean admin, int presupuesto, int preferencia, Double tiempo){
		this.username = username;
		this.password = PassHash(password);
		this.admin = admin;
		this.preferencia = preferencia;
		this.presupuesto = presupuesto;
		this.tiempo = tiempo;
	}
	
	public Usuario(String username, String password, int presupuesto, int preferencia, Double tiempo) {
		this(username, password, false, presupuesto, preferencia, tiempo);
	}

	public Usuario(Integer id, String username, String password, int presupuesto, int preferencia, Double tiempo) {
		this(id, username, password, false, presupuesto, preferencia, tiempo, true);
	}
	
	public boolean auth(String password) {
		try {
			return this.password.equals(PassHash(password));
		} catch (Exception err) {
			return false;
		}
	}

	public boolean hashAuth(String password) {
		try {
			return this.password.equals(password);
		} catch (Exception err) {
			return false;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return admin;
	}

	public Integer getId() {
		return id;
	}

	public int getpresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(int presupuesto) {
		if(presupuesto > 0)
			this.presupuesto = presupuesto;
	}
	
	public boolean isActive() {
		return active;
	}

	public int getPreferencia() {
		return this.preferencia;
	}
	
	public void setPreferencia(int preferencia){
		this.preferencia = preferencia;
	}

	public Double getTiempo() {
		return this.tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}


	private static String PassHash(String password){
		try{
			String strSalt = "@_M(FbH_H[m24!qU";
			byte[] salt = strSalt.getBytes();
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = f.generateSecret(spec).getEncoded();
			Base64.Encoder enc = Base64.getEncoder();
			return enc.encodeToString(hash);
		} catch(Exception e){
			return null;
		}
    }
}
