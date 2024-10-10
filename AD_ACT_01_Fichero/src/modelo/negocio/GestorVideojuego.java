package modelo.negocio;

import modelo.entidad.Usuario;
import modelo.entidad.Videojuego;
import modelo.persistencia.DaoUsuarioFichero;
import modelo.persistencia.DaoVideojuegoFichero;

public class GestorVideojuego {
	
	private DaoVideojuegoFichero dvf;

	/**
	 * Método que guarda un videojuego pasado por parametro
	 * 
	 * @param v el videojuego a guardar
	 * @return <b>0</b> el videojuego pasado por parametro es null, <b>1</b> el nombre
	 *         esta vacio o solo tiene espacios en blanco, <b>2</b> el password esta
	 *         vacio o solo tiene espacios en blanco, <b>3</b> el usuario se ha
	 *         guardado <b>666</b> en caso de que haya algún problema en el de
	 *         entrada salida
	 */
	public int guardar(Videojuego v) {
		if (v == null) {
			return 0;
		}

		dvf = new DaoVideojuegoFichero();
		try {
			if (v.getNombre().isBlank()) {
				return 1;
			} else if (v.getCompania().isBlank()) {
				return 2;	
			} else if (v.getNota().isBlank()) {
			}else {
				dvf.registrar(v);
				return 3;
			}
		} catch (Exception e) {
			return 666;
		}
	}
	
	/**
	 * Método que valida un videojuego pasado por parametro contra un videojuego 
	 * guardado en la persistencia. Un usuario esta validado cuando el nombre, 
	 * la empresa y la nota guardada coincide con el videojuego pasado por parametro.
	 * 
	 * @param v videojuego a validar/comparar
	 * @return <b>0</b> el videojuego no existe, <b>1</b> el videojuego existe y es
	 *         valido, <b>2</b> el videojuego existe pero no es valido y <b>3</b> en
	 *         caso de que haya algún problema en el de entrada salida
	 */
	public int validar(Usuario u) {
		duf = new DaoUsuarioFichero();
		try {
			Usuario uFichero = duf.getByName(u.getNombre());
			if (uFichero == null) {
				return 0;
			}

			if (uFichero.equals(u)) {
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			return 3;
		}
	}
	
	/**
	 * Metedo que agrega un videojuego pasado por parametro
	 * @param nombre nombre del videojuego que se quiere guardar
	 * @param compania nombre de la compania que se quiere agregar
	 * @param nota valor entero de la nota que tiene el videojuego
	 * @throws Exception 
	 */
	public void agregarVideojuego(String nombre, String compania, String nota) throws Exception {
        // Validaciones
        if (nombre.length() < 3) {
            throw new Exception("El nombre del videojuego debe tener al menos 3 letras.");
        }

        if (compania.length() < 5) {
            throw new Exception("La compañía debe tener al menos 5 letras.");
        }

        try {
            int notaNumerica = Integer.parseInt(nota);
            if (notaNumerica < 0 || notaNumerica > 100) {
                throw new Exception("La nota debe ser un número entero entre 0 y 100.");
            }
            DaoVideojuegoFichero.guardarVideojuego(nombre, compania, notaNumerica);
        } catch (NumberFormatException e) {
            throw new Exception("La nota debe ser un número entero.");
        }
    }

}
