package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestDaoVideojuegoFichero {

	@Test
	void testRegistrar() {
		fail("Not yet implemented");
	}

	@Test
	void testGetListaVideojuegos() {
		fail("Not yet implemented");
	}

	@Test
	void testGetByName() {
		fail("Not yet implemented");
	}

	@Test
	void testBorrarVideojuego() {
		fail("Not yet implemented");
	}

	
	/*
	 * package main;

import modelo.entidad.Videojuego;
import modelo.persistencia.DaoVideojuegoFichero;

public class MainPrueba {

	public static void main(String[] args) {
		DaoVideojuegoFichero dvj = new DaoVideojuegoFichero();
		
		Videojuego juego;
		try {
			juego = dvj.getByName("Pokemon");
			boolean b = dvj.borrarVideojuego(juego);
			if (b) {
				System.out.println("Borrado");
			} else {
				System.out.println("No borrado");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Videojuego judego = new Videojuego();
//		
//		judego.setNombre("Pokemon");
//		judego.setCompania("Nintendo");
//		judego.setNota(100);
//		
//		try {
//			dvj.registrar(judego);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		

	}

}

	 * 
	 * */
}
