package ar.unrn.tp8.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import ar.unrn.tp8.modelo.Persona;
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
		String sql = "select p.nombre,t.numero " + "from personas p, telefonos t "
				+ "where p.id = t.id_persona and p.id = ?";
		try (Connection conn = obtenerConexion(); PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			Set<Telefono> telefonos = new HashSet<Telefono>();
			String nombrePersona = null;
			while (result.next()) {
				nombrePersona = result.getString(1);
				telefonos.add(new Telefono(result.getString(2)));
			}
			return new Persona(id, nombrePersona, telefonos);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
