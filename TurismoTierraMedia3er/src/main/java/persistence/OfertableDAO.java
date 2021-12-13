package persistence;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Ofertable;
import persistence.commons.GenericDAO;

public interface OfertableDAO extends GenericDAO<Ofertable>{
    public abstract Ofertable toOferta(ResultSet res) throws SQLException;
    public abstract Ofertable find(String nombre);
}
