package ar.unrn.tp8.main;

import ar.unrn.tp8.accesos.PersonaDao;
import ar.unrn.tp8.modelo.Persona;
import ar.unrn.tp8.modelo.Telefono;

public class Main {
	public static void main(String args[]) {
		PersonaDao dao = new PersonaDao();
		Persona p = dao.personaPorId(1);
		System.out.println(p.nombre());
		for (Telefono telefono : p.telefonos()) {
			System.out.println(telefono);
		}
	}

}
