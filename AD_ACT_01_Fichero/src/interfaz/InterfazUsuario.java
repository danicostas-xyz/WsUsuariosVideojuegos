package interfaz;

import java.util.ArrayList;
import java.util.Scanner;

import modelo.entidad.Usuario;
import modelo.entidad.Videojuego;
import modelo.negocio.GestorUsuario;
import modelo.negocio.GestorVideojuego;
import modelo.persistencia.DaoVideojuegoFichero;

public class InterfazUsuario {

	private GestorUsuario gu;
	private GestorVideojuego gv;
	private Scanner scString = new Scanner(System.in);
	private Scanner sc = new Scanner(System.in);

	/**
	 * Muestra la interfaz de bienvenida de la aplicación y gestiona el proceso de
	 * validación del usuario. Permite hasta tres intentos para que el usuario
	 * ingrese sus datos correctamente.
	 *
	 * <p>
	 * El método realiza las siguientes acciones:
	 * <ul>
	 * <li>Muestra un mensaje de bienvenida.</li>
	 * <li>Solicita al usuario que ingrese sus datos.</li>
	 * <li>Valida los datos ingresados mediante la clase {@link GestorUsuario}.</li>
	 * <li>Ofrece hasta tres intentos de validación para que el usuario ingrese
	 * correctamente su nombre de usuario y contraseña.</li>
	 * <li>Proporciona mensajes al usuario según el resultado de la validación:</li>
	 * <ul>
	 * <li>Si el usuario no existe, se informa al usuario.</li>
	 * <li>Si el usuario es correcto, se inicia la aplicación y se muestra un
	 * mensaje de bienvenida.</li>
	 * <li>Si el nombre de usuario o la contraseña son incorrectos, se permite un
	 * nuevo intento.</li>
	 * <li>Si ocurre un error de acceso, se informa al usuario y se sugiere intentar
	 * más tarde.</li>
	 * </ul>
	 * </ul>
	 * </p>
	 *
	 * <p>
	 * Al finalizar los intentos de validación, se imprime un mensaje que indica que
	 * la aplicación ha terminado.
	 * </p>
	 *
	 */

	public void mostrarInterfaz() {
		System.out.println("Bienvenidos a nuestra app :)");
		Usuario usuario = null;
		gu = new GestorUsuario();
		int respuesta = 0;

		int contador = 0;
		boolean validado = false;

		while (contador < 3 && !validado) {
			usuario = pedirDatos();
			respuesta = gu.validar(usuario);
			switch (respuesta) {
			case 0:
				System.out.println("Usuario no existe");
				break;
			case 1:
				System.out.println("Usuario correcto, bienvenido a la app");
				validado = true;
				iniciarAplicacion(usuario);
				break;
			case 2:
				System.out.println("Usuario y/o password incorrectos");
				contador++;
				break;
			case 666:
				System.out.println("Error de acceso. Intentelo mas tarde");
				break;
			}

		}

		System.out.println("Fin de la aplicación");

	}

	/**
	 * Muestra la interfaz una vez logueado el usuario. Muestra un mensaje de
	 * bienvenida y te dice el nombre del perfil con el que has entrado.
	 * <p>
	 * Este metodo muestras las siguienetes opciones
	 * </p>
	 * <ul>
	 * <li>Dar de alta un Usuario nuevo (invoca dicho metodo)</li>
	 * <li>Dar de alta un Videojuego nuevo (invoca dicho metodo)</li>
	 * <li>Mostrar la lista de Videojuegos (invoca dicho metodo)</li>
	 * <li>Borrar el videojuego (invoca dicho metodo)</li>
	 * <li>Opcion no valida o incorrecta</li>
	 * <li></li>
	 * </ul>
	 * 
	 * @param u usuario que se pasa por parametro
	 */
	private void iniciarAplicacion(Usuario u) {
		System.out.println("========================================");
		System.out.println("        ¡Bienvenido a la aplicación!     ");
		System.out.println("========================================");
		System.out.println("         Perfil de: " + u.getNombre());
		System.out.println("========================================");
		System.out.println();

		int opcion = 0;
		do {
			opcion = menu();
			switch (opcion) {
			case 1:
				darAltaUsuario();
				break;
			case 2:
				darAltaVideojuego();
				break;
			case 3:
				mostrarListaVideojuegos();
				break;
			case 4:
				borrarVideojuego();
				break;
			default:
				System.out.println("Opcion incorrecta wachiiin");
			}
		} while (opcion != 0);
	}

	/**
	 * Metodo que permite borrar un Videojuego. Se le pedira al usuario que
	 * introduzca el nombre del videojuego, el nombre de la compañia y su nota sobre
	 * 100
	 * <p>
	 * Las opciones son las siguientes:
	 * </p>
	 * <ul>
	 * <li>Comunica al usuario que el videojuego introducido no existe</li>
	 * <li>Comunica al usuario que el videojuego introducido se ha borrado
	 * correctamente</li>
	 * <li>Comunica al usuario que el videojuego introducido existe pero hay algun
	 * error</li>
	 * <li>Comunica al usuario que ha habido un error</li>
	 * </ul>
	 */
	private void borrarVideojuego() {
		System.out.println("Que videojuego deseas borrar");
		Videojuego videojuego = pedirDatosVideojuego();
		gv = new GestorVideojuego(DaoVideojuegoFichero.FICHERO);
		int opcion = gv.borrar(videojuego);
		switch (opcion) {
		case 0:
			System.out.println("El videojuego no existe muñón");
			break;
		case 1:
			System.out.println("El videojuego se ha borrado correctamente" + " FELICIDADES!!!!!!");
			break;
		case 2:
			System.out.println("El videojuego existe pero no es valido");
			break;
		case 666:
			System.err.println("Ha ocurrido un ERROR");
			break;
		default:
			break;
		}

	}

	/**
	 * Muestra una lista de videojuegos en la consola.
	 *
	 * Este método utiliza un gestor de videojuegos para obtener una lista de
	 * objetos de tipo Videojuego y luego imprime sus detalles en la consola. Se
	 * mostrará el nombre, la compañía y la nota de cada videojuego. Si la lista
	 * está vacía, se mostrará un mensaje indicándolo.
	 *
	 * <p>
	 * Formato de la salida en consola:
	 * </p>
	 * 
	 *
	 * Si no hay videojuegos en la lista, se mostrará el siguiente mensaje:
	 * 
	 * <pre>
	 *  No hay videojuegos en la lista.
	 * </pre>
	 */

	private void mostrarListaVideojuegos() {
		gv = new GestorVideojuego(DaoVideojuegoFichero.FICHERO);
		ArrayList<Videojuego> listaVideojuegos = new ArrayList<Videojuego>();
		try {
			listaVideojuegos = gv.mostrar();
			System.out.println("========================================");
			System.out.println("         Lista de Videojuegos           ");
			System.out.println("========================================");

			int contador = 1;
			for (Videojuego videojuego : listaVideojuegos) {
				System.out.println(" Videojuego #" + contador + ":");
				System.out.println("   Nombre:      " + videojuego.getNombre());
				System.out.println("   Compañía:    " + videojuego.getCompania());
				System.out.println("   Nota:        " + videojuego.getNota() + "/100");
				System.out.println("----------------------------------------");
				contador++;
			}

			if (listaVideojuegos.isEmpty()) {
				System.out.println(" No hay videojuegos en la lista.");
			}
			System.out.println("========================================");
		} catch (Exception e) {
			System.out.println("Ha sucedido algún error en la consulta, inténtelo de nuevo más tarde");
			System.out.println("Error: " + e.getClass());
		}

	}

	/**
	 * Metodo que permite dar de alta un videojuego. El metodo invocara el metodo
	 * pedirDatosVideojuego y los guardara mediante el método creado en la capa
	 * gestora.
	 * <p>
	 * El menu es el siguiente
	 * </p>
	 * <ul>
	 * <li>Videojuego en blanco o con menos de 3 letras</li>
	 * <li>Compañia en blanco o con menos de 5 letras</li>
	 * <li>Nota por debajo de 0 o superior a 100</li>
	 * <li>Videojuego guardado con exito</li>
	 * <li>Error de acceso</li>
	 * </ul>
	 */
	private void darAltaVideojuego() {
		Videojuego videojuego = pedirDatosVideojuego();
		gv = new GestorVideojuego(DaoVideojuegoFichero.FICHERO);
		int respuesta = gv.guardar(videojuego);
		switch (respuesta) {
		case 1:
			System.out.println("Videojuego en blanco o con menos de 3 letras");
			break;
		case 2:
			System.out.println("Compañia en blanco o con menos de 5 letras");
			break;
		case 3:
			System.out.println("Nota por debajo de 0 o superior a 100");
			break;
		case 4:
			System.out.println("Videojuego guardado con exito");
			break;

		case 666:
			System.out.println("Error de acceso. Intentalo mas tarde ");
			break;
		}

	}

	private void darAltaUsuario() {
		Usuario usuario = pedirDatos();
		int respuesta = gu.guardar(usuario);
		switch (respuesta) {
		case 1:
			System.out.println("Usuario en blanco o con solo espacios en blanco");
			break;
		case 2:
			System.out.println("Password en blanco o con solo espacios en blanco");
			break;
		case 3:
			System.out.println("Usuario guardado con exito!! :) :)");
			break;
		case 666:
			System.out.println("Error de acceso. Intentelo mas tarde. Codigo 666");
			break;
		}
	}

	/**
	 * Metodo que muestra un menú y devuelve un entero. El metodo muestra el
	 * siguiente menú.
	 * <h4>Menu principal</h4>
	 * <ul>
	 * <li>Registrar Usuario</li>
	 * <li>Agregar Videjuego</li>
	 * <li>Lista Videojuego</li>
	 * <li>Borrar videojuego</li>
	 * <li>Salir del programa</li>
	 * </ul>
	 * 
	 * @return opcion, numero entero
	 */

	private int menu() {
		boolean correcto = false;
		String opcion = null;
		int iOpcion = 0;
		while (!correcto) {
			System.out.println("              MENÚ PRINCIPAL");
			System.out.println("========================================");
			System.out.println("| 1. Registar Usuario                  |");
			System.out.println("| 2. Agregar Videojuego                |");
			System.out.println("| 3. Lista Videojuego                  |");
			System.out.println("| 4. Borrar Videojuego                 |");
			System.out.println("========================================");
			System.out.println("| 0. Salir del prograna                |");
			System.out.println("========================================");
			System.out.print("Selecciona una opción: ");
			opcion = scString.nextLine();
			try {
				iOpcion = Integer.parseInt(opcion);
				if (iOpcion >= 0 && iOpcion <= 4) {
					correcto = true;
				} else {
					System.out.println("Opcion incorrecta");
				}
			} catch (Exception e) {
				System.out.println("Felix no cuela ya");
			}
		}
		return iOpcion;
	}

	private Usuario pedirDatos() {
		System.out.println("Introduzca el nombre: ");
		String nombre = scString.nextLine();
		System.out.println("Introduzca el password: ");
		String pass = scString.nextLine();
		Usuario u = new Usuario();
		u.setNombre(nombre);
		u.setPassword(pass);
		return u;
	}

	/**
	 * Metodo que pide el nombre del vieojuego, el nombre de la compañia y su nota
	 * al usuario y devuelve el objeto Videjuego
	 * 
	 * @return videojuego, devuelve el objeto videojuego.
	 */
	private Videojuego pedirDatosVideojuego() {
		System.out.println("Introduzca el nombre del Videojuego");
		String nombre = scString.nextLine();
		System.out.println("Introduce el nombre de la Compañia");
		String nombreCompania = scString.nextLine();
		System.out.println("Introduce la nota del Videojuego");
		int notaVideojuego = sc.nextInt();
		Videojuego videojuego = new Videojuego();
		videojuego.setNombre(nombre);
		videojuego.setCompania(nombreCompania);
		videojuego.setNota(notaVideojuego);
		return videojuego;
	}
}
