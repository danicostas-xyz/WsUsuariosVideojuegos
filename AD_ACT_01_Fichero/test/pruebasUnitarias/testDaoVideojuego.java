package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.entidad.Videojuego;
import modelo.persistencia.DaoVideojuegoFichero;

class TestDaoVideojuego {
	
	
    @BeforeEach
    void setUp() throws IOException {
        // Antes de cada prueba, asegúrate de crear un archivo vacío
        File file = new File("Videojuegos");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Después de cada prueba, eliminamos el archivo temporal
        Files.deleteIfExists(Path.of("Videojuegos"));
    }

	@Test
	void ficheroNoExistente() {
		File file = new File("Videojuegos");
		//Borramos el fichero para que nos salte el error
		file.delete();
		DaoVideojuegoFichero dvf = new DaoVideojuegoFichero();
		Videojuego vj = new Videojuego();
		vj.setCompania("Nintendo");
		vj.setNombre("Pokemon");
		vj.setNota(100);
		// Se trata de una funcion de JUnit que se usa para verificar que se lanza
		// una excepcion cuando se ejecuta el bloque. Se usa una funcion lambda
		Exception excepcion = assertThrows(Exception.class, ()->{
			dvf.registrar(vj);
		});
		// Aqui comprobamos que el texto que pone sea igual al texto que envia la excepcion
		
		assertEquals("Error con fichero. Inténtelo de nuevo más tarde", excepcion.getMessage());
	}
	
	@Test
	void ficheroExiste() throws Exception {
		// Creamos el fichero videojuegos
		File file = new File("Videojuegos");
		// Creamos la clase para poder invocar el método
		DaoVideojuegoFichero dvf = new DaoVideojuegoFichero();
		// Creamos el objeto Videojuego y le establecemos unos valores
		Videojuego vj = new Videojuego();
		vj.setCompania("Nintendo");
		vj.setNombre("Pokemon");
		vj.setNota(100);
		// Aplicamos el metodo registra
		dvf.registrar(vj);
		// Ahora lo que hacemos es leer el fichero y comprobar si son iguales los resultados
	    try (BufferedReader br = new BufferedReader(new FileReader("Videojuegos"))) {
	            String linea = br.readLine();
	            assertNotNull(linea);
	            assertEquals("Pokemon_Nintendo_100", linea);
	        }
		
	}

	@Test
	void testGetListaVideojuegosConFormatoIncorrecto()throws Exception{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Videojuegos"))) {
			bw.write("Pokemon_Nintendo_100");
			bw.write("Pokemon/Nintendo/100");
			
		}
		// Probamos que se lanza la excepcion
		DaoVideojuegoFichero dvf = new DaoVideojuegoFichero();
		Exception excepcion = assertThrows(Exception.class, ()->{
			dvf.getListaVideojuegos();
		});
			
				
	}
	

	
	
	
}


