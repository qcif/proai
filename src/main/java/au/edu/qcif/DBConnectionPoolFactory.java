package au.edu.qcif;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class DBConnectionPoolFactory {

	private static ConnectionSource connectionSource = null;
	
	public static synchronized ConnectionSource getConnectionPool(String url, String username, String password, String driver) throws SQLException, ClassNotFoundException {
		if(connectionSource == null) {
			Class.forName(driver);
			connectionSource = new JdbcPooledConnectionSource(url, username, password);
		}
		return connectionSource;
	}

	public static ConnectionSource getConnectionPool() {
		return connectionSource;
	}
}
