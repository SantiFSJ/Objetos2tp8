package ar.unrn.tp8.accesos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.unrn.tp8.modelo.DBFacade;

public class FacadeImpl implements DBFacade {
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL_DB = "jdbc:mysql://localhost:3306/";
	protected static String DB = "objetos2_tp8";

	protected static String user = "root";
	protected static String pass = "";
	protected static Connection conn = null;

	private static void connect() throws SQLException {
		try {
			conn = DriverManager.getConnection(URL_DB + DB + "?useSSL=false", user, pass);
		} catch (SQLException sqlEx) {
			throw sqlEx;
		}
	}

	@Override
	public void open() {
		try {
			connect();
		} catch (SQLException sqlEx) {
			throw new RuntimeException();
		}
	}

	@Override
	public List<Map<String, String>> queryResultAsAsociation(String sql) {
		try (PreparedStatement statement = conn.prepareStatement(sql);) {

			List<Map<String, String>> lista = new ArrayList<Map<String, String>>();

			ResultSet result = statement.executeQuery();

			ResultSetMetaData rsMeta = result.getMetaData();
			int cantColumnas = rsMeta.getColumnCount();

			while (result.next()) {
				for (int i = 1; i <= cantColumnas; i++) {
					lista.add(Map.of(rsMeta.getColumnName(i), result.getString(i)));
				}
			}
			return lista;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String[]> queryResultAsArray(String sql) {
		try (PreparedStatement statement = conn.prepareStatement(sql);) {

			List<String[]> lista = new ArrayList<String[]>();

			ResultSet result = statement.executeQuery();

			ResultSetMetaData rsMeta = result.getMetaData();
			int cantColumnas = rsMeta.getColumnCount();

			while (result.next()) {
				String[] array = new String[cantColumnas];
				for (int i = 1; i <= cantColumnas; i++) {
					array[i - 1] = result.getString(i);
				}
				lista.add(array);
			}
			return lista;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getCause());
		}
	}

}
