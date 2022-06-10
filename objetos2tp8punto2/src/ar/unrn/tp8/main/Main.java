package ar.unrn.tp8.main;

import java.util.List;
import java.util.Map;

import ar.unrn.tp8.accesos.FacadeImpl;
import ar.unrn.tp8.modelo.DBFacade;

public class Main {
	public static void main(String args[]) {
		DBFacade db = new FacadeImpl();
		String sql = "SELECT * FROM personas";

		db.open();

		List<Map<String, String>> lista = db.queryResultAsAsociation(sql);

		for (Map<String, String> m : lista) {
			for (Map.Entry<String, String> entry : m.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}
		}

		System.out.println("//////////////////////");

		List<String[]> listaArrays = db.queryResultAsArray(sql);

		for (String[] a : listaArrays) {
			for (int i = 0; i < a.length; i++) {
				System.out.println(a[i]);
			}
			// a.toString();
		}

		db.close();
	}

}
