package persistence.impl;

import persistence.ItinerariosDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Itinerarios;
import model.Ofertable;
import persistence.ItinerariosDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.DAOFactory;
import persistence.commons.MissingDataException;

public class ItinerariosDAOImpl implements ItinerariosDAO{
    @Override
    public Itinerarios find(Integer id) {
        try{
            String sql = "SELECT * FROM Itinerarios WHERE Id = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultados = statement.executeQuery();
            Itinerarios itn = null;
            if(resultados.next()){
                itn = toItinerario(resultados);
            }
            return itn;
        } catch(Exception e){
            throw new MissingDataException(e);
        }

    }

    @Override
    public List<Itinerarios> findAll() {
        try{
            List<Itinerarios> salida = new ArrayList<Itinerarios>();
            String sql = "SELECT * FROM Itinerarios";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultados = statement.executeQuery();
            while(resultados.next()){
                salida.add(toItinerario(resultados));
            }
            return salida;
        } catch(Exception e){
            throw new MissingDataException(e);
        }
    }

    @Override
    public int insert(Itinerarios t) {
        try{
            String sql;
            if(t.getAtracciones().isBlank()){
                sql = "INSERT INTO Itinerarios (Usuario,Promociones,Precio,Tiempo) VALUES (?,?,?,?)";
            } else {
                sql = "INSERT INTO Itinerarios (Usuario,Atracciones,Precio,Tiempo) VALUES (?,?,?,?)";
            }
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            if(t.getAtracciones().isBlank()){
                statement.setString(1, t.getUsuario());
                statement.setString(2, t.getAtracciones());
                statement.setInt(3, t.getPrecio());
                statement.setDouble(4, t.getTiempo());
            } else {
                statement.setString(1, t.getUsuario());
                statement.setInt(2, t.getPromociones());
                statement.setInt(3, t.getPrecio());
                statement.setDouble(4, t.getTiempo());
            }
            int rows = statement.executeUpdate();
            return rows;
        } catch(Exception e){
            throw new MissingDataException(e);
        }
    }

    @Override
    public int update(Itinerarios t) {
        // ignorame
        return 0;
    }

    @Override
    public int delete(Integer id) {
        // ignorame
        return 0;
    }

    @Override
    public List<Ofertable> findAllByUser(Integer id) {
        try{
            List<Ofertable> salida = new ArrayList<Ofertable>();
            String sql = "SELECT * FROM Itinerarios WHERE Usuario = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, DAOFactory.getUsuarioDAO().find(id).getUsername());
            ResultSet resultados = statement.executeQuery();
            while(resultados.next()){
                if(resultados.getString(3).isBlank()){
                    salida.add( DAOFactory.getOfertableDAO().find( resultados.getInt(4) ) );
                } else {
                    salida.add( DAOFactory.getOfertableDAO().find( resultados.getString(3) ) );
                }
            }
            return salida;
        } catch(Exception e){
            throw new MissingDataException(e);
        }
    }

    @Override
    public Itinerarios toItinerario(ResultSet res) throws SQLException {
        return new Itinerarios(res.getInt("Id"), res.getString("Usuario"), res.getString("Atracciones"), res.getInt("Promociones"), res.getInt("Precio"), res.getDouble("Tiempo"));
    }
    
}
