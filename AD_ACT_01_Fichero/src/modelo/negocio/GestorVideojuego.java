package modelo.negocio;

import java.util.ArrayList;

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
	 *         esta vacio o solo tiene espacios en blanco, o tiene menos de 3 letras
	 *         <b>2</b> la compañia esta vacia o solo tiene espacios en blanco, o tiene 
	 *         menos de 5 letras<b>3</b> la nota es inferior a 0 o superior a 100 <b>4</b>
	 *         el videojuego se ha registrado correctamente <b>666</b> en caso de que 
	 *         haya algún problema en el de entrada salida
	 */
	public int guardar(Videojuego v) {
		if (v == null) {
			return 0;
		}

		dvf = new DaoVideojuegoFichero(DaoVideojuegoFichero.FICHERO);
		try {
			if (v.getNombre().isBlank() || v.getNombre().length() < 3) {
				return 1;
			} else if (v.getCompania().isBlank() || v.getCompania().length() < 5) {
				return 2;	
			} else if (!esNumeroValido(String.valueOf(v.getNota())) || v.getNota() < 0 || v.getNota() > 100) {
		        return 3;  // Nota inválida
			}else {
				dvf.registrar(v);
				return 4;
			}
		} catch (Exception e) {
			return 666;
		}
	}
	
	public boolean esNumeroValido(String str) {
	    if (str == null || str.isBlank()) {
	        return false;  // Si la cadena está vacía o en blanco, es inválida
	    }
	    try {
	        Integer.parseInt(str);  // Intenta convertir la cadena a un entero
	        return true;  // Si no lanza excepción, es un número válido
	    } catch (NumberFormatException e) {
	        return false;  // Si ocurre una excepción, la cadena no es un número válido
	    }
	}
	
	/**
	 * Método que valida un videojuego pasado por parametro contra un videojuego 
	 * guardado en la persistencia. Un videojuego esta validado cuando el nombre, 
	 * la empresa y la nota guardada coincide con el videojuego pasado por parametro.
	 * 
	 * @param v videojuego a validar/comparar
	 * @return <b>0</b> el videojuego no existe, <b>1</b> el videojuego existe y es
	 *         valido, <b>2</b> el videojuego existe pero no es valido y <b>666</b> en
	 *         caso de que haya algún problema en el de entrada salida
	 */
	public int validar(Videojuego v) {
		dvf = new DaoVideojuegoFichero(DaoVideojuegoFichero.FICHERO);
		try {
			Videojuego vFichero = dvf.getByName(v.getNombre());
			if (vFichero == null) {
				return 0;
			}

			if (vFichero.equals(v)) {
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			return 666;
		}
	}
	/**
	 * Metodo que borra un videojuego pasado por parámetro
	 * @param v videojuego a borrar
	 * @return <b>0</b> el videojuego no existe, <b>1</b> el videojuego se ha 
	 * 			borrado correctamente <b>2</b> el videojuego existe pero no es
	 * 		 	valido y <b>666</b> en caso de que haya algún problema en el de 
	 * 			entrada salida
	 */
	public int borrar(Videojuego v) {
		
		if (v == null) {
			return 0;
		}
		
		dvf = new DaoVideojuegoFichero(DaoVideojuegoFichero.FICHERO);
		try {
			
			if(validar(v) == 1) {
				dvf.borrarVideojuego(v, false);
				return 1;
			}else if(validar(v) == 2) {
				return 2;
				
			}else{
				return 0; //Esperar a probar el programa, puede fallar!!!
			}
		} catch (Exception e) { 
			return 666;
		}
		
	}
	/**
	 * Metodo que muestra un arrayList de de videojuegos
	 * @return arrayList de videojuegos
	 */
	public ArrayList<Videojuego> mostrar() {
		dvf = new DaoVideojuegoFichero(DaoVideojuegoFichero.FICHERO);
		ArrayList<Videojuego> lista = null;
		try {
			lista = dvf.getListaVideojuegos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
		
	}

}
