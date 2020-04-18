package connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import exception.DBException;

public class DB {

	private static final String configs_file_properties = "db.properties";

	private static Connection connection = null;

	private static Properties getProperties() {
		File file_path = new File("src/" + configs_file_properties);

		try (FileInputStream is = new FileInputStream(file_path)) {

			Properties props = new Properties();

			props.load(is);

			is.close();

			return props;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection() {
		if (connection == null) {

			try {
				Properties props = getProperties();

				String url = props.getProperty("databaseurl");

				connection = DriverManager.getConnection(url, props);

				return connection;

			} catch (SQLException e) {
				throw new DBException("Erro ao conectar no banco: " + e.getMessage());
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

}
