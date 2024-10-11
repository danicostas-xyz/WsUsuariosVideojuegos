package modelo.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.entidad.Videojuego;

/**
 * Clase que implementa la interfaz IDaoVideojuego para persistir datos. Esta
 * implementación persiste los datos en un fichero .txt
 */
public class DaoVideojuegoFichero implements IDaoVideojuego {

	public static final String NOMBRE_FICHERO = "videojuegos.txt";

	/**
	 * Método que registra un videojuego en la persistencia
	 *
	 * @param vj es el videojuego a registrar
	 * @throws Exception si hay algún problema con el fichero
	 */
	@Override
	public void registrar(Videojuego vj) throws Exception {

		File f = new File(NOMBRE_FICHERO);
		if (!f.exists()) {
			throw new Exception("Error con fichero. Inténtelo de nuevo más tarde");
		}
		escribir(vj);
	}

	/**
	 * Método que devuelve un ArrayList de Videojuegos con los Videojuegos que
	 * existan en la persistencia
	 *
	 * @return listaVideojuegos es un ArrayList de Videojuego
	 * @throws Exception en caso de que suceda un error en la lectura del fichero
	 */
	@Override
	public ArrayList<Videojuego> getListaVideojuegos() throws Exception {

		try (FileReader fr = new FileReader(NOMBRE_FICHERO); BufferedReader br = new BufferedReader(fr)) {

			ArrayList<Videojuego> listaVideojuegos = new ArrayList<Videojuego>();
			String linea = br.readLine();

			while (linea != null) {

				Videojuego vj = new Videojuego();
				vj.setNombre(linea.split("_")[0]);
				vj.setCompania(linea.split("_")[1]);
				vj.setNota(Integer.parseInt(linea.split("_")[2]));
				listaVideojuegos.add(vj);

				linea = br.readLine();
			}
			return listaVideojuegos;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Método que dado un nombre pasado por parametro busca su coincidencia en el
	 * fichero "videojuegos.txt" y en caso de que lo encuentre lo devuelve junto con
	 * su compañía y su nota
	 *
	 * @param nombre el nombre a buscar en el fichero
	 * @return Videojuego en caso de que este en en fichero, null en caso contrario
	 * @throws Exception, en caso de que haya algún problema en el fichero de
	 *                    entrada salida
	 */
	public Videojuego getByName(String nombreVideojuego) throws Exception {
		Videojuego vj = null;

		try (FileReader fr = new FileReader(NOMBRE_FICHERO); BufferedReader br = new BufferedReader(fr)) {
			String linea = br.readLine(); // linea = "NOMBRE_COMPANIA_NOTA"
			while (linea != null) {
				if (nombreVideojuego.equals(linea.split("_")[0])) {
					vj = new Videojuego();
					vj.setNombre(linea.split("_")[0]);
					vj.setCompania(linea.split("_")[1]);
					vj.setNota(Integer.parseInt(linea.split("_")[2]));

					return vj;
				}
				linea = br.readLine();
			}

		} catch (Exception e) {
			throw e;
		}
		return vj; // Si llega hasta aquí, es que vj = null (no se encontró el videojuego)
	}

	/**
	 * Método que dado un Videojuego pasado por parámetro, lo busca en la
	 * persistencia y, en caso de que exista, elimina la primera ocurrencia o todas
	 * las ocurrencias, dependiendo del valor booleano de borrarTodos
	 * 
	 * @param videojuegoABorrar el Videojuego a buscar en fichero, borrarTodos la
	 *                          opción a elegir: si es true, se borran todas las
	 *                          ocurrencias del Videojuego a buscar. Si es false, se
	 *                          elimina la primera ocurrencia.
	 * @return
	 *         <ul>
	 *         <li><b>true</b> en caso de que el Videojuego exista y se borre al
	 *         menos una vez</li>
	 *         <li><b>false</b> en caso de que no se borre porque no exista</li>
	 *         </ul>
	 * @throws Exception en caso de que ocurra algún error de lectura/escritura con
	 *                   el fichero
	 * 
	 */
	@Override
	public boolean borrarVideojuego(Videojuego videojuegoABorrar, boolean borrarTodos) throws Exception {

		ArrayList<Videojuego> listaVideojuegos = getListaVideojuegos();
		ArrayList<Videojuego> listaVideojuegosModificada = getListaVideojuegos();

		/*
		 * listaVideojuegosModificada.remove() modifica la lista en la cual se invoca el
		 * método y elimina la primera ocurrencia del Videojuego pasado por parámetro.
		 * Además, devuelve un boolean. True en caso de que se haya encontrado el objeto
		 * pasado por parámetro y se haya borrado o False en caso contrario
		 * 
		 */

		// Si borrarTodos es true, se aumenta el valor de i tantas veces como
		// listavideojuegos.remove() sea true.
		// listavideojuegos.remove() es true si el juego existe. Cada vez que se invoca
		// el remove(), se borra la primera ocurrencia

		// Si borrarTodos es false, se aumenta el valor de i a 1
		int i = 0;
		if (borrarTodos) {
			while (listaVideojuegos.remove(videojuegoABorrar)) {
				i++;
			}
		} else
			i = 1;

		boolean vjEsBorrado = false;

		// Bucle que ejecuta listaVideojuegosModificada.remove() i veces, en función del
		// resultado obtenido arriba
		for (int j = 0; j < i; j++) {
			vjEsBorrado = listaVideojuegosModificada.remove(videojuegoABorrar);
		}

		File f = new File(NOMBRE_FICHERO);

		if (vjEsBorrado) {
			if (!f.exists()) {
				throw new Exception("Error con fichero. Inténtelo de nuevo más tarde");
			} else {
				f.delete();
				for (Videojuego vj : listaVideojuegosModificada) {
					escribir(vj);
				}
			}
		}

		return vjEsBorrado;

	}

	/**
	 * Método privado que accede al fichero para escribir, en una línea, un
	 * Videojuego pasado por parámetro.
	 * 
	 * @param <b>v</b> El Videojuego a escribir en fichero.
	 * @throws IOException En caso de que arroje excepción por algún problema en la
	 *                     comunicación con el fichero
	 */
	private void escribir(Videojuego v) throws IOException {
		try (FileWriter fw = new FileWriter(NOMBRE_FICHERO, true); BufferedWriter bw = new BufferedWriter(fw)) {

			bw.write(v.toString());
			bw.newLine();
		}
	}

}
