
package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows; // Importa assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test; // Importa Test de JUnit 5

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.entidad.Videojuego;
import modelo.persistencia.DaoVideojuegoFichero;

class TestDaoVideojuegoDani {

	private static final String ARCHIVO_PRUEBAS = "videojuegos_prueba2.txt";
	private DaoVideojuegoFichero dao;

	@BeforeEach
	void setUp() throws IOException {
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
	}

	@Test
	void testGetByName_videojuegoEncontrado() throws Exception {
		Videojuego vj = new Videojuego();
		vj.setNombre("Pokemon");
		vj.setCompania("Nintendo");
		vj.setNota(100);

		Videojuego resultado = dao.getByName("Pokemon");
		assertEquals(resultado, vj);
	}

	@Test
	void testGetByName_videojuegoNoEncontrado() throws Exception {
		Videojuego resultado = dao.getByName("Fifa");
		assertNull(resultado);
	}

	@Test
	void testGetByName_archivoVacio() throws Exception {
		// Crea un archivo de prueba vac�o
		dao = new DaoVideojuegoFichero("vacio.txt");
		Videojuego resultado = dao.getByName("Uncharted_Sony_90");
		assertNull(resultado);
	}

	@Test
	void testGetByName_archivoNoExiste() throws Exception {
		// Intenta buscar en un archivo que no existe
		dao = new DaoVideojuegoFichero("archivoInexistente");

		assertThrows(FileNotFoundException.class, () -> {
			dao.getByName("PokemonNintendo100");
		});
	}
	
	@Test
	void testGetNombreFichero() {
		String resultadoObtenido = dao.getNombreFichero();
		assertEquals(resultadoObtenido, ARCHIVO_PRUEBAS);
	}
	
	@Test
	void testSetNombreFichero() {
		String nombre = "fichero";
		dao.setNombreFichero(nombre);
		assertTrue(dao.getNombreFichero() == nombre);
	}
	
	@Test
	void testGetListaVideojuegosConFicheroNoEncontrado() throws Exception {
		dao = new DaoVideojuegoFichero("fic");
		assertThrows(FileNotFoundException.class, () -> {
			dao.getListaVideojuegos();
		});

	}
	
	@Test
	void testGetListaVideojuegosConFicheroVacio()throws Exception{
		// Esta prueba es muy facil solo hay que ejecutar el metodo 
		// y nos tendra que decir que la cadena esta vacía
		dao = new DaoVideojuegoFichero("vacio.txt");
		ArrayList<Videojuego> listaVideojuegos = dao.getListaVideojuegos();
		assertTrue(listaVideojuegos.isEmpty());	
	}
	
	@Test
	void testGetListaVideojuegosConContenidoCorrecto() throws Exception{
		
		dao = new DaoVideojuegoFichero("videojuegos_prueba3");

		ArrayList<Videojuego>listaVideojuego = dao.getListaVideojuegos();
		
		assertTrue(listaVideojuego.size() > 0);
		
	
			
		
	}
	
	
	

}