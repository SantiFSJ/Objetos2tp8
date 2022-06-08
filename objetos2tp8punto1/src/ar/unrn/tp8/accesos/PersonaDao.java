package ar.unrn.tp8.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import ar.unrn.tp8.modelo.Persona;
import ar.unrn.tp8.modelo.SetProxy;
import ar.unrn.tp8.modelo.Telefono;

public class PersonaDao {

	private Connection obtenerConexion() {
		try {
			return ConnectionManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Persona personaPorId(int id) {
		String sql = "select p.nombre " + "from personas p " + "where p.id = ?";
		try (Connection conn = obtenerConexion(); PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			Set<Telefono> proxy = new SetProxy<Telefono>(new PersonaDao(), id);

			String nombrePersona = null;
			while (result.next()) {
				nombrePersona = result.getString(1);
			}
			conn.close();
			return new Persona(id, nombrePersona, proxy);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Set<Telefono> telefonosDePersona(int id) {
		String sql = "select t.numero " + "from telefonos t " + "where t.id_persona = ?";

		try (Connection conn = obtenerConexion(); PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			Set<Telefono> telefonos = new HashSet<Telefono>();

			while (result.next()) {
				telefonos.add(new Telefono(result.getString(1)));
			}
			conn.close();
			return telefonos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
