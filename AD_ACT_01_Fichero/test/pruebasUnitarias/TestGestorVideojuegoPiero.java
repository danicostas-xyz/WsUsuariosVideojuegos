package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.entidad.Videojuego;
import modelo.negocio.GestorVideojuego;
import modelo.persistencia.DaoVideojuegoFichero;

class TestGestorVideojuegoPiero {

	private GestorVideojuego gv;
	private Videojuego vd;

	private static final String ARCHIVO_PRUEBAS = "videojuegos_prueba_piero.txt";
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

	@BeforeEach
	void setUp() throws Exception {
		gv = new GestorVideojuego(ARCHIVO_PRUEBAS);
		vd = new Videojuego();
	}

	@AfterEach
	void tearDown() throws Exception {
		gv = null;
		vd = null;
	}

	@Test
	void testGuardarNull() {
		vd = null;
		int resultadoObtenido = gv.guardar(vd);
		int resultadoEsperado = 0;

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testNombreInvalido() {
		vd.setNombre("");
		int resultadoObtenido = gv.guardar(vd);
		int resultadoEsperado = 1;

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testCompaniaInvalido() {
		vd.setNombre("Mariokar");
		vd.setCompania("");
		int resultadoObtenido = gv.guardar(vd);
		int resultadoEsperado = 2;

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testNotaInvalido() {
		vd.setNombre("Mariokar");
		vd.setCompania("Nintendo switch");
		vd.setNota(111);
		int resultadoObtenido = gv.guardar(vd);
		int resultadoEsperado = 3;

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testGuardarValido() {
		vd.setNombre("Mariokar");
		vd.setCompania("Nintendo switch");
		vd.setNota(99);
		int resultadoObtenido = gv.guardar(vd);
		int resultadoEsperado = 4;

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testError() {
		vd.setNombre("Mariokar");
		vd.setCompania(null);
		vd.setNota(100);

		int resultadoEsperado = 666;
		int resultadoObtenido = gv.guardar(vd);

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testValidarNoExiste() {
		vd.setNombre("Mariokar99");
		vd.setCompania("Nintendo switch00");
		vd.setNota(99);

		int resultadoEsperado = 0;
		int resultadoObtenido = gv.validar(vd);

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testValidarExisteYValido() {
		vd.setNombre("Death Stranding");
		vd.setCompania("Kojima Productions");
		vd.setNota(99);

		int resultadoEsperado = 1;
		int resultadoObtenido = gv.validar(vd);

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	@Test
	void testValidarExisteNoValido() {
		vd.setNombre("Mariokar");
		vd.setCompania("Nintendo switch");
		vd.setNota(90);

		int resultadoEsperado = 2;
		int resultadoObtenido = gv.validar(vd);

		assertEquals(resultadoEsperado, resultadoObtenido);

	}

	/*
	 * Solo puede llegar a conseguir el Error cuando el archivo videojuego.txt no
	 * exista.
	 * 
	 * @Test void testValidarError() { vd.setNombre("Mariokar");
	 * vd.setCompania("Nintendo switch"); vd.setNota(90);
	 * 
	 * int resultadoEsperado = 666; int resultadoObtenido = gv.validar(vd);
	 * 
	 * assertEquals(resultadoEsperado, resultadoObtenido);
	 * 
	 * }
	 */

	@Test
	void testBorrarNoExiste() {

		int resultadoEsperado = 0;
		int resultadoObtenido = gv.borrar(vd);

		assertEquals(resultadoEsperado, resultadoObtenido);
	}

	@Test
	void testBorrarExiste() {
		vd.setNombre("Mariokar");
		vd.setCompania("Nintendo switch");
		vd.setNota(99);

		int resultadoEsperado = 1;
		int resultadoObtenido = gv.borrar(vd);

		assertEquals(resultadoEsperado, resultadoObtenido);
	}

}
