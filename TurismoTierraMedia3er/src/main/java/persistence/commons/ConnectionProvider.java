package persistence.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
		if (connection == null) {
			connection = DriverManager.getConnection("jdbc:sqlite:D:\\CHICOS\\matias\\Documentos\\Documentos Facultad\\Concordia programa\\TurismoTierraMedia3\\TurismoTierraMedia3\\TurismoTierraMedia3er\\DBTierraMedia.db");
		}
		return connection;
	}

}