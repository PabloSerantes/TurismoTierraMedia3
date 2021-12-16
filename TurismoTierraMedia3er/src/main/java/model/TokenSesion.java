package model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import persistence.commons.DAOFactory;

public class TokenSesion {
	int id;
	String username;
	String token;
	LocalDate fecha;
	
	public TokenSesion(String username) {
		this.username = username;
		this.token = UUID.randomUUID().toString();
		this.fecha = LocalDate.now().plusDays(5);
	}
	
	public TokenSesion(int id, String username, String token, LocalDate fecha) {
		this.id = id;
		this.username = username;
		this.token = token;
		this.fecha = fecha;
	}
	
	
	public boolean isValid() {
		return ( this.fecha.compareTo(LocalDate.now())>=0) && ( Objects.nonNull(DAOFactory.getUsuarioDAO().findByUsername(this.username) ) );
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public void update() {
		this.token = UUID.randomUUID().toString();
		this.fecha = LocalDate.now().plusDays(5);
	}
}
