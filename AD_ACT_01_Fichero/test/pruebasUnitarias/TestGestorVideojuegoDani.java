package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modelo.entidad.Videojuego;
import modelo.negocio.GestorUsuario;
import modelo.negocio.GestorVideojuego;
import modelo.persistencia.DaoVideojuegoFichero;

class TestGestorVideojuegoDani {

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
	void testMostrarArraylistVideojuego() throws Exception {
		GestorVideojuego gv = new GestorVideojuego(ARCHIVO_PRUEBAS);
		List<Videojuego> listaVideojuegos = gv.mostrar();

		assertTrue(listaVideojuegos.equals(dao.getListaVideojuegos()));

	}

}
