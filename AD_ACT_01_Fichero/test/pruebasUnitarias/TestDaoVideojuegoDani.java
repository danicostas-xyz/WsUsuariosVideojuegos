
package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows; // Importa assertThrows
import org.junit.jupiter.api.Test; // Importa Test de JUnit 5

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.entidad.Videojuego;
import modelo.persistencia.DaoVideojuegoFichero;

class TestDaoVideojuegoDani {

	private static final String ARCHIVO_PRUEBAS = "videojuegos_prueba2.txt";
	private DaoVideojuegoFichero dao;

	@BeforeEach
	public void setUp() throws IOException {
		dao = new DaoVideojuegoFichero(ARCHIVO_PRUEBAS);
	}

	@Test
	public void testGetByName_videojuegoEncontrado() throws Exception {
		Videojuego vj = new Videojuego();
		vj.setNombre("Pokemon");
		vj.setCompania("Nintendo");
		vj.setNota(100);

		Videojuego resultado = dao.getByName("Pokemon");
		assertEquals(resultado, vj);
	}

	@Test
	public void testGetByName_videojuegoNoEncontrado() throws Exception {
		Videojuego resultado = dao.getByName("Fifa");
		assertNull(resultado);
	}

	@Test
	public void testGetByName_archivoVacio() throws Exception {
		// Crea un archivo de prueba vacío
		dao = new DaoVideojuegoFichero("vacio.txt");
		Videojuego resultado = dao.getByName("Uncharted_Sony_90");
		assertNull(resultado);
	}

	@Test
	public void testGetByName_archivoNoExiste() throws Exception {
		// Intenta buscar en un archivo que no existe
		dao = new DaoVideojuegoFichero("archivoInexistente");

		assertThrows(FileNotFoundException.class, () -> {
			dao.getByName("PokemonNintendo100");
		});
	}

//	    private void crearArchivoPrueba(String contenido) throws IOException {
//	    	try (FileWriter writer = new FileWriter(ARCHIVO_PRUEBAS, true); BufferedWriter bw = new BufferedWriter(writer)) {
//				bw.newLine();
//	            writer.write(contenido);
//	        }
//	    }

}
