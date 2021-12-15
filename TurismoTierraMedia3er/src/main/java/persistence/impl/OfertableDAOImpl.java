package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import model.Ofertable;
import persistence.OfertableDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class OfertableDAOImpl implements OfertableDAO{
    @Override
    public Ofertable find(Integer id){
        try{
            String sql = "SELECT * FROM Promocion WHERE Id = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultados = statement.executeQuery();
            Ofertable oferta = null;
            if(resultados.next()){
                oferta = toOferta(resultados);
            }
            return oferta;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    public Ofertable find(String Nombre){
        try {
            String sql = "SELECT * FROM Atraccion WHERE Nombre = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, Nombre);
            ResultSet resultados = statement.executeQuery();
            Ofertable oferta = null;
            if(resultados.next()){
                oferta = toOferta(resultados);
            } else {
                sql = "SELECT * FROM Promocion WHERE Nombre = ?";
                conn = ConnectionProvider.getConnection();
                statement = conn.prepareStatement(sql);
                statement.setString(1, Nombre);
                resultados = statement.executeQuery();
                if(resultados.next()){
                    oferta = toOferta(resultados);
                }
            }
            if(Objects.isNull(oferta)) {
            	throw new Exception(Nombre);
            }
            return oferta;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public List<Ofertable> findAll() {
        try {
			String sql = "SELECT * FROM Atraccion";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Ofertable> ofertas = new LinkedList<Ofertable>();
			while (resultados.next()) {
				ofertas.add(toOferta(resultados));
			}

            sql = "SELECT * FROM Promocion";
            conn = ConnectionProvider.getConnection();
            statement = conn.prepareStatement(sql);
            resultados = statement.executeQuery();
            while (resultados.next()){
                ofertas.add(toOferta(resultados));
            }
			return ofertas;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
    }

    @Override
    public int update(Ofertable t){
        try {
            String sql;
            Connection conn;
            PreparedStatement statement;
            if(t.getConformacion().isBlank()){
                sql = "Update Atraccion SET Nombre = ?, SET Costo = ?, SET Tiempo = ?, SET Cupo = ?, SET Tipo = ? WHERE Id = ?";
                conn = ConnectionProvider.getConnection();
                statement = conn.prepareStatement(sql);
                statement.setString(1, t.getNombre());
                statement.setDouble(2, t.getPrecio());
                statement.setDouble(3, t.getTiempo());
                statement.setInt(4, t.getCupo());
                statement.setInt(5, t.getTipo());
                statement.setInt(6, t.getId());
            } else {
                sql = "Update Promocion SET Nombre = ?, SET Tipo = ?, SET Atracciones = ?, SET Especial = ?, SET TipoPromocion = ? WHERE Id = ?";
                conn = ConnectionProvider.getConnection();
                statement = conn.prepareStatement(sql);
                statement.setString(1, t.getNombre());
                statement.setInt(2, t.getTipo());
                statement.setString(3, t.getConformacion());
                statement.setString(4, t.getEspecial());
                statement.setString(5,t.getTipoPromocion());
                statement.setInt(6,t.getId());
            }
            int rows = statement.executeUpdate();
            return rows;
        } catch (Exception e) {
			throw new MissingDataException(e);
		}
    }

    @Override
    public int insert(Ofertable t){
        try {
            String sql;
            Connection conn;
            PreparedStatement statement;
            if(t.getConformacion().isBlank()){
                sql = "INSERT INTO Atraccion (Nombre,Costo,Tiempo,Cupo,Tipo,Active) VALUES (?,?,?,?,?,?)";
                conn = ConnectionProvider.getConnection();
                statement = conn.prepareStatement(sql);
                statement.setString(1, t.getNombre());
                statement.setDouble(2, t.getPrecio());
                statement.setDouble(3, t.getTiempo());
                statement.setInt(4, t.getCupo());
                statement.setInt(5, t.getTipo());
                statement.setInt(6, 1);
            } else {
                sql = "INSERT INTO Promocion (Tipo,Nombre,Atracciones,Especial,TipoPromocion,Active) VALUES (?,?,?,?,?,?)";
                conn = ConnectionProvider.getConnection();
                statement = conn.prepareStatement(sql);
                statement.setInt(1, t.getTipo());
                statement.setString(2, t.getNombre());
                statement.setString(3, t.getConformacion());
                statement.setString(4, t.getEspecial());
                statement.setString(5, t.getTipoPromocion());
                statement.setInt(6, 1);
            }
            int rows = statement.executeUpdate();
            return rows;
        } catch (Exception e) {
			throw new MissingDataException(e);
		}
    }

    @Override
    public int delete(Integer id){
        return 0;
    }

    public int delete(String nombre){
        try{
            String sql = "UPDATE Promocion SET active = 0 WHERE Nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			int rows = statement.executeUpdate();
            if( rows == 0 ){
                sql = "UPDATE Atraccion SET active = 0 WHERE Nombre = ?";
                conn = ConnectionProvider.getConnection();
                statement = conn.prepareStatement(sql);
                statement.setString(1, nombre);
			    rows = statement.executeUpdate();
            }
			return rows;
        } catch (Exception e) {
			throw new MissingDataException(e);
		}
    }

    @Override
    public Ofertable toOferta(ResultSet res) throws SQLException {
        Ofertable salida;
        if(res.getMetaData().getColumnLabel(2).equals("Tipo")){
            salida = new Ofertable(res.getInt("Id"),
            res.getString("Nombre"),
            res.getString("Atracciones"),
            res.getInt("Tipo"),
            res.getString("TipoPromocion"),
            res.getString("Especial"),
            res.getInt("Active")==1);
        } else {
            salida = new Ofertable(res.getInt("Id"),
            res.getString("Nombre"), res.getInt("Costo"), res.getDouble("Tiempo"), res.getInt("Cupo"), res.getInt("Tipo"), res.getInt("Active")==1);
        }
        System.out.println();
        return salida;
    }
}
