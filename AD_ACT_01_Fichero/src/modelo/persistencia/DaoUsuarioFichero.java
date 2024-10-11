package modelo.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import modelo.entidad.Usuario;

public class DaoUsuarioFichero implements IDaoUsuario{
	private static final String NOMBRE_FICHERO = "usuarios.dat";

	/**
	 * Método que dado un nombre pasado por parametro busca su coincidencia en el
	 * fichero "usuarios.dat" y en caso de que lo encuentre lo devuelve junto con su
	 * password
	 * 
	 * @param nombre el nombre a buscar en el fichero
	 * @return Usuario en caso de que este en en fichero, null en caso contrario
	 * @throws Exception, en caso de que haya algún problema en el fichero de
	 *                    entrada salida
	 */
	public Usuario getByName(String nombre) throws Exception {
		Usuario usuario = null;

		try (FileReader fr = new FileReader(NOMBRE_FICHERO); BufferedReader br = new BufferedReader(fr)) {
			String cadena = br.readLine();// USUARIO/PASSWORD
			while (cadena != null) {
				String[] cadenaPartida = cadena.split("/");
				String nombreUsuario = cadenaPartida[0];
				String passwordUsuario = cadenaPartida[1];
				if (nombre.equals(nombreUsuario)) {
					usuario = new Usuario();
					usuario.setNombre(nombreUsuario);
					usuario.setPassword(passwordUsuario);
					return usuario;
				}
				cadena = br.readLine();
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Método que dado un usuario lo persista en el fichero "usuarios.dat". Se
	 * añadirá a la última línea. Se persistirá en formato "NOMBRE/PASSWORD"
	 * 
	 * @param u es el usuario que queremos persistir
	 * @throws Exception, en caso de que haya algún problema en el fichero de
	 *                    entrada salida
	 */
	public void registrar(Usuario u) throws Exception {
		File f = new File(NOMBRE_FICHERO);
		if (!f.exists()) {
			throw new Exception("Fichero NO existe! :(");
		}

		try (FileWriter fw = new FileWriter(NOMBRE_FICHERO, true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.newLine();
			bw.write(u.toString());
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Método que dada una password pasada por parámetro, la devuelve hasheada
	 * mediante el algoritmo SHA-256
	 * 
	 * @param pass es la password a hashear
	 * @return la password hasheada en caso de que no haya ocurrido ningún error
	 * @throws NoSuchAlgorithmException en caso de que no se haya podido ejecutar el
	 *                                  algoritmo criptográfico
	 */
	
	@SuppressWarnings("unused") // Unused temporally 
	private String hashPass(String pass) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(pass.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			String hex = Integer.toHexString(0xff & digest[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();

	}
}
