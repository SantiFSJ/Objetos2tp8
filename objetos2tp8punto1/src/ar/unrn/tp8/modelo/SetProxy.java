package ar.unrn.tp8.modelo;

import java.util.Set;

public abstract class SetProxy implements Set {
	@Override
	<T> T[] toArray(T[] a) {

		return telefonos.toArray(new Telefono[telefonos.size()]);
	}
}
