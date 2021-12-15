package persistence.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
/*
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;*/

import model.Usuario;
import persistence.UsuarioDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public Usuario find(Integer id) {
		try {
			String sql = "SELECT * FROM Usuarios WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet resultados = statement.executeQuery();

			Usuario usuario = null;
			if (resultados.next()) {
				usuario = toUsuario(resultados);
			}

			return usuario;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public List<Usuario> findAll() {
		try {
			String sql = "SELECT * FROM Usuarios";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Usuario> usuarios = new LinkedList<Usuario>();
			while (resultados.next()) {
				usuarios.add(toUsuario(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int insert(Usuario t) {
		try {
			String sql = "INSERT INTO Usuarios (Nombre, Preferencia, Presupuesto, TiempoDisponible, Active, Password) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getUsername());
			statement.setInt(2, t.getPreferencia());
			statement.setInt(3, t.getpresupuesto());
			statement.setDouble(4, t.getTiempo());
			statement.setInt(5,1);
			statement.setString(6, t.getPassword());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int update(Usuario t) {
		try {
			String sql = "UPDATE Usuarios SET Nombre = ?, Password = ?, Presupuesto = ?, Preferencia = ? WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getUsername());
			//statement.setString(2, PassHash(t.getPassword() ) );
			statement.setDouble(3, t.getpresupuesto());
			statement.setInt(4, t.getPreferencia());
			statement.setInt(5, t.getId());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int delete(Integer id) {
		try {
			String sql = "UPDATE Usuarios SET active = 0 WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public Usuario findByUsername(String username) {
		try {
			String sql = "SELECT * FROM Usuarios WHERE name = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			
			ResultSet resultados = statement.executeQuery();

			Usuario usuario = null;
			if (resultados.next()) {
				usuario = toUsuario(resultados);
			}

			return usuario;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public Boolean compare(String hash, int id){
		try{
			String sql = "SELECT EXISTS(SELECT * FROM Usuarios WHERE (password=?) and (id=?) and (active=1)) as respuesta";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, hash);
			statement.setInt(2, id);
			return statement.executeQuery().getInt("respuesta") == 1;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public Usuario toUsuario(ResultSet res) throws SQLException {
		return null;
	}

	/*
	private static String PassHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        String strSalt = "@_M(FbH_H[m24!qU";
        byte[] salt = strSalt.getBytes();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(hash);
      
    }
	*/
}