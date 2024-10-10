package modelo.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import modelo.entidad.Videojuego;

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

		try (FileWriter fw = new FileWriter(NOMBRE_FICHERO, true); BufferedWriter bw = new BufferedWriter(fw)) {

			bw.write(vj.toString());
			bw.newLine();
		} catch (Exception e) {
			throw e;
		}

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
	public Videojuego getByName(String nombre) throws Exception {
		Videojuego vj = null;

		try (FileReader fr = new FileReader(NOMBRE_FICHERO); BufferedReader br = new BufferedReader(fr)) {
			String cadena = br.readLine();// NOMBRE_COMPANIA_NOTA
			while (cadena != null) {
				if (nombre.equals(cadena.split("_")[0])) {
					vj = new Videojuego();
					vj.setNombre(cadena.split("_")[0]);
					vj.setCompania(cadena.split("_")[1]);
					vj.setNota(Integer.parseInt(cadena.split("_")[2]));

					return vj;
				}
				cadena = br.readLine();
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Método que dado un Videojuego pasado por parámetro, lo busca en la
	 * persistencia y, en caso de que exista, lo elimina.
	 * 
	 * @param videojuegoABorrar el Videojuego a buscar en fichero
	 * @return
	 *         <ul>
	 *         <li><b>true</b> en caso de que el Videojuego exista y se borre</li>
	 *         <li><b>false</b> en caso de que no se borre porque no exista</li>
	 *         </ul>
	 * @throws Exception en caso de que ocurra algún error con el fichero
	 * 
	 */
	@Override
	public boolean borrarVideojuego(Videojuego videojuegoABorrar) throws Exception {

		ArrayList<Videojuego> listaVideojuegos = getListaVideojuegos();
		ArrayList<Videojuego> listaVideojuegosModificada = listaVideojuegos;

		boolean resultado = listaVideojuegosModificada.remove(videojuegoABorrar);

		File f = new File(NOMBRE_FICHERO);

		if (resultado) {
			if (!f.exists()) {
				throw new Exception("Error con fichero. Inténtelo de nuevo más tarde");
			} else {
				f.delete();
				for (Videojuego vj : listaVideojuegosModificada) {

					try (FileWriter fw = new FileWriter(NOMBRE_FICHERO, true);
							BufferedWriter bw = new BufferedWriter(fw)) {

						bw.write(vj.toString());
						bw.newLine();
					} catch (Exception e) {
						throw e;
					}
				}
			}
		}

		return resultado;

	}

}
