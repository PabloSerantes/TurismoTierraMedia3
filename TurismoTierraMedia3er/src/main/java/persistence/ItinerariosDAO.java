package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Itinerarios;
import model.Ofertable;
import persistence.commons.GenericDAO;

public interface ItinerariosDAO extends GenericDAO<Itinerarios>{
    public abstract List<Ofertable> findAllByUser(Integer id);
    public abstract Itinerarios toItinerario(ResultSet res) throws SQLException;
}
