package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.TokenSesion;
import persistence.TokenSesionDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class TokenSesionDAOImpl implements TokenSesionDAO {

	@Override
	public TokenSesion find(Integer id) {
		try{
			String sql = "SELECT * FROM Tokens WHERE Id = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultados = statement.executeQuery();
            TokenSesion token = null;
            if(resultados.next()){
                token = toTokenSesion(resultados);
            }
            return token;
		} catch (Exception e) {
            throw new MissingDataException(e);
        }
	}
	
	@Override
	public TokenSesion findByUsername(String Username) {
		try{
			String sql = "SELECT * FROM Tokens WHERE Username = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, Username);
            ResultSet resultados = statement.executeQuery();
            TokenSesion token = null;
            if(resultados.next()){
                token = toTokenSesion(resultados);
            }
            return token;
		} catch (Exception e) {
            throw new MissingDataException(e);
        }
	}

	@Override
	public List<TokenSesion> findAll() {
		try{
			String sql = "SELECT * FROM Tokens";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultados = statement.executeQuery();
            List<TokenSesion> salida = new ArrayList<TokenSesion>();
            if(resultados.next()){
                salida.add(toTokenSesion(resultados));
            }
            return salida;
		} catch (Exception e) {
            throw new MissingDataException(e);
        }
	}

	@Override
	public int insert(TokenSesion t) {
		try {
			if( this.exist(t.getUsername()) ) {
				return -1;
			} else {
				if(t.isValid()) {
					String sql = "INSERT INTO Tokens (Username,Token,Fecha) VALUES (?,?,?)";
		            Connection conn = ConnectionProvider.getConnection();
		            PreparedStatement statement = conn.prepareStatement(sql);
		            statement.setString(1, t.getUsername() );
		            statement.setString(2, t.getToken());
		            statement.setString(3, t.getFecha().toString());
		            int salida = statement.executeUpdate();
		            return salida;
				} else {
					return -1;
				}
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int update(TokenSesion t) {
		try {
			if( !( this.exist(t.getUsername()) ) || !(t.isValid())) {
				return -1;
			} else {
				t.update();
				String sql = "UPDATE Tokens SET Token = ?. SET Fecha = ?";
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, t.getToken());
				statement.setString(2, t.getFecha().toString() );
				int salida = statement.executeUpdate();
				return salida;
			}
		} catch(Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int delete(Integer id) {
		try {
			TokenSesion t = this.find(id);
			if(Objects.nonNull(t)) {
				String sql = "DELETE FROM Tokens WHERE id=?";
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, id);
				int salida = statement.executeUpdate();
				return salida;
			}
			return -1;
		} catch(Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int delete(String username) {
		try {
			TokenSesion t = this.findByUsername(username);
			if(Objects.nonNull(t)) {
				String sql = "DELETE FROM Tokens WHERE id=?";
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, t.getId());
				int salida = statement.executeUpdate();
				return salida;
			}
			return -1;
		} catch(Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	@Override
	public boolean exist(String nombre) {
		try{
			String sql = "SELECT * FROM Tokens WHERE Username = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            ResultSet resultados = statement.executeQuery();
            TokenSesion token = null;
            if(resultados.next()){
                token = toTokenSesion(resultados);
            }
            return Objects.nonNull(token);
		} catch (Exception e) {
            throw new MissingDataException(e);
        }
	}

	@Override
	public boolean isValid(String nombre) throws SQLException {
		try{
			String sql = "SELECT * FROM Tokens WHERE Username = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            ResultSet resultados = statement.executeQuery();
            TokenSesion token = null;
            if(resultados.next()){
                token = toTokenSesion(resultados);
            }
            if(Objects.nonNull(token)) {
            	return token.isValid();
            } else {
            	return false;
            }
		} catch (Exception e) {
            throw new MissingDataException(e);
        }
	}

	public TokenSesion toTokenSesion(ResultSet entrada) throws SQLException  {
		LocalDate fecha = LocalDate.parse(entrada.getString("Fecha"));
		return new TokenSesion(entrada.getInt("Id"), entrada.getString("Username"), entrada.getString("Token"), fecha);
	}
}
