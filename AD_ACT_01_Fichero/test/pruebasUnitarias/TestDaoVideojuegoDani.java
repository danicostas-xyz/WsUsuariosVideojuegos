
package pruebasUnitarias;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.entidad.Videojuego;
import modelo.persistencia.DaoVideojuegoFichero;

class TestDaoVideojuegoDani {

	private static final String ARCHIVO_PRUEBAS = "videojuegos_prueba_dani.txt";
	static private DaoVideojuegoFichero dao;

	@BeforeAll
	static void creaFicheroConVideojuegos() throws Exception {
		File ficheroPruebas = new File(ARCHIVO_PRUEBAS);
		ficheroPruebas.createNewFile();
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
		
		Videojuego vjPrueba = new Videojuego();
		vjPrueba.setNombre("Death Stranding");
		vjPrueba.setCompania("Kojima Productions");
		vjPrueba.setNota(99);
		
		Videojuego vjPrueba2 = new Videojuego();
		vjPrueba2.setNombre("Doom Eternal");
		vjPrueba2.setCompania("Bethesda");
		vjPrueba2.setNota(89);
		
		Videojuego vjPrueba3 = new Videojuego();
		vjPrueba3.setNombre("Mortal Kombat 11");
		vjPrueba3.setCompania("NetherRealm");
		vjPrueba3.setNota(92);
		
		dao.registrar(vjPrueba);
		dao.registrar(vjPrueba2);
		dao.registrar(vjPrueba3);
	}
	
	@AfterAll
	static void limpiaFicheroPruebas() throws Exception {
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
		dao.borrarVideojuego(dao.getByName("Death Stranding"), true);
		dao.borrarVideojuego(dao.getByName("Doom Eternal"), true);
		dao.borrarVideojuego(dao.getByName("Mortal Kombat 11"), true);
	}

	@Test
	void testGetByName_videojuegoEncontrado() throws Exception {
		Videojuego vj = new Videojuego();
		vj.setNombre("Death Stranding");
		vj.setCompania("Kojima Productions");
		vj.setNota(99);

		Videojuego resultado = dao.getByName("Death Stranding");
		assertEquals(resultado, vj);
	}

	@Test
	void testGetByName_videojuegoNoEncontrado() throws Exception {
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
		Videojuego resultado = dao.getByName("Fifa");
		assertNull(resultado);
	}

	@Test
	void testGetByName_archivoVacio() throws Exception {
		// Crea un archivo de prueba vac�o
		File ficheroVacio = new File("vacio.txt");
		ficheroVacio.createNewFile();
		dao = new DaoVideojuegoFichero("vacio.txt");
		Videojuego resultado = dao.getByName("Uncharted_Sony_90");
		assertNull(resultado);
	}

	@Test
	void testGetByName_archivoNoExiste() throws Exception {
		// Intenta buscar en un archivo que no existe
		dao = new DaoVideojuegoFichero("archivoInexistente");

		assertThrows(FileNotFoundException.class, () -> {
			dao.getByName("Death Stranding");
		});
	}
	
	@Test
	void testGetNombreFichero() {
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
		String resultadoObtenido = dao.getNombreFichero();
		assertEquals(resultadoObtenido, ARCHIVO_PRUEBAS);
	}
	
	@Test
	void testSetNombreFichero() {
		String nombre = "fichero";
		dao.setNombreFichero(nombre);
		assertTrue(dao.getNombreFichero().equals(nombre));
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
		File ficheroVacio = new File("vacio.txt");
		ficheroVacio.createNewFile();
		dao = new DaoVideojuegoFichero("vacio.txt");
		ArrayList<Videojuego> listaVideojuegos = dao.getListaVideojuegos();
		assertTrue(listaVideojuegos.isEmpty());	
	}
	
	@Test
	void testGetListaVideojuegosConContenidoCorrecto() throws Exception {
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
		ArrayList<Videojuego> listaVideojuego = dao.getListaVideojuegos();
	    assertTrue(listaVideojuego.size() > 0);
	}
	
	
	@Test
	void testBorrarUnaOcurrenciaVideojuego() throws Exception {
		Videojuego vj = new Videojuego();
		vj.setNombre("Fifa 2005");
		vj.setCompania("EA Sports");
		vj.setNota(79);
		
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
		
		dao.registrar(vj);
		
		vj = new Videojuego();
		vj.setNombre("Fifa 2005");
		vj.setCompania("EA Sports");
		vj.setNota(79);
		
		dao.registrar(vj);
		
		assertTrue(dao.borrarVideojuego(vj, false));
		assertTrue(dao.getByName("Fifa 2005") != null);
		dao.borrarVideojuego(vj, false);
		
	}
	
	@Test
	void testBorrarTodasOcurrenciaVideojuego() throws Exception {
		Videojuego vj = new Videojuego();
		vj.setNombre("Fifa 2005");
		vj.setCompania("EA Sports");
		vj.setNota(79);
		
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
		
		dao.registrar(vj);
		
		vj = new Videojuego();
		vj.setNombre("Fifa 2005");
		vj.setCompania("EA Sports");
		vj.setNota(79);
		
		dao.registrar(vj);
		
		assertTrue(dao.borrarVideojuego(vj, true));
		assertTrue(dao.getByName("Fifa 2005") == null);

	}
	
	@Test
	void testBorrarVideojuegoErrorConFichero() throws Exception {
		Videojuego vj = new Videojuego();
		vj.setNombre("Fifa 2005");
		vj.setCompania("EA Sports");
		vj.setNota(79);
		
		File fichero = new File("fichero");
		fichero.createNewFile();
		
		dao = new DaoVideojuegoFichero("fichero");
		dao.registrar(vj);
		fichero.delete();
		
		assertThrows(Exception.class, () -> {
			
			dao.borrarVideojuego(vj, false);
		});
		


	}
	

	
	
	
	

}