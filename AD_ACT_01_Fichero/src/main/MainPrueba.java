package main;

import java.util.Iterator;

import modelo.entidad.Videojuego;
import modelo.persistencia.DaoVideojuegoFichero;

public class MainPrueba {
	public static void main(String[] args) {
		DaoVideojuegoFichero a = new DaoVideojuegoFichero();
		
		
		
		
		Videojuego vj = new Videojuego();
		vj.setNombre("GTA: Sasn Andreas");
		vj.setCompania("Rockstar");
		vj.setNota(98);
		
		boolean opcion = false;
		
		try {
			for (int i = 0; i < 4; i++) {
				a.borrarVideojuego(vj, opcion);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (opcion) {
			System.out.println("Borradas todas las ocurrencias de " + vj.getNombre());
		} else {
			System.out.println("Borrada 1 ocurrencia de " + vj.getNombre());
		}
		
		
	}
}
