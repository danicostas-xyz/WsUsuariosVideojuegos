
package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.entidad.Videojuego;
import modelo.persistencia.DaoVideojuegoFichero;


class testDaoVideojuego {
	
	
	 private static final String ARCHIVO_PRUEBAS = "videojuegos_prueba.txt";
	 private DaoVideojuegoFichero dao;
	 private Videojuego vj;
	 
	    @BeforeEach
	    public void setUp() throws IOException {
	    	dao = new DaoVideojuegoFichero();
	    }

	    @AfterEach
	    public void tearDown() throws IOException {
	        // Elimina el archivo de pruebas después de cada prueba
	        new File(ARCHIVO_PRUEBAS).delete();
	    }

	    @Test
	    public void testGetByName_videojuegoEncontrado() throws Exception {
	        // Crea un archivo de prueba con el videojuego
	        crearArchivoPrueba("Pokemon_Nintendo_100");
	        dao = new DaoVideojuegoFichero();
	        Videojuego resultado = dao.getByName("Pokemon_Nintendo_100");

	        assertEquals("Pokemon", resultado.getNombre());
	        assertEquals("Nintendo", resultado.getCompania());
	        assertEquals(100, resultado.getNota());
	    }

	    @Test
	    public void testGetByName_videojuegoNoEncontrado() throws Exception {
	        // Crea un archivo de prueba sin el videojuego
	        crearArchivoPrueba("Uncharted_Sony_90");
	        dao = new DaoVideojuegoFichero();
	        Videojuego resultado = dao.getByName("Pokemon_Nintendo_100");

	        assertNull(resultado);
	    }

	    @Test
	    public void testGetByName_archivoVacio() throws Exception {
	        // Crea un archivo de prueba vacío
	        crearArchivoPrueba("");
	        dao = new DaoVideojuegoFichero();
	        Videojuego resultado = dao.getByName("Uncharted_Sony_90");

	        assertNull(resultado);
	    }

	    @Test
	    public void testGetByName_formatoInvalido() throws Exception {
	        // Crea un archivo de prueba con formato inválido
	    	dao = new DaoVideojuegoFichero();
	        crearArchivoPrueba("Pokemon/Nintendo");

	        assertThrows(NumberFormatException.class, () -> dao.getByName("Pokemon_Nintendo_100"));
	    }

	    @Test
	    public void testGetByName_archivoNoExiste() throws Exception {
	        // Intenta buscar en un archivo que no existe
	    	dao = new DaoVideojuegoFichero();
	        Videojuego resultado = dao.getByName("PokemonNintendo100");

	        assertNull(resultado); 
	    }

	   
	    
	  
	   
	    private void crearArchivoPrueba(String contenido) throws IOException {
	        try (FileWriter writer = new FileWriter(ARCHIVO_PRUEBAS)) {
	            writer.write(contenido);
	        }
	    }
	
}

