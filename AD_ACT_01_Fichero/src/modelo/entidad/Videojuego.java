package modelo.entidad;

import java.util.Objects;

public class Videojuego {
	private String nombre;
	private String compania;
	private int nota;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCompania() {
		return compania;
	}
	public void setCompania(String compania) {
		this.compania = compania;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	@Override
	public int hashCode() {
		return Objects.hash(compania, nombre, nota);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Videojuego other = (Videojuego) obj;
		return Objects.equals(compania, other.compania) && Objects.equals(nombre, other.nombre) && nota == other.nota;
	}
	
	/**
	 * Metodo toString modificado que devuelve los datos 
	 * de la misma manera que se desean guardar.
	 */
	@Override
	public String toString() {
		return   nombre + "_" + compania + "_" + nota ;
	}
	
	
	

}
