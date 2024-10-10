package interfaz;

import java.util.Scanner;

import modelo.entidad.Usuario;
import modelo.entidad.Videojuego;
import modelo.negocio.GestorUsuario;

public class InterfazUsuario {

	private GestorUsuario gu = null;
	private Scanner scString = new Scanner(System.in);
	private Scanner sc = new Scanner(System.in);

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
		}while(opcion != 0);
	}

	private void borrarVideojuego() {
		// TODO Auto-generated method stub
		
	}

	private void mostrarListaVideojuegos() {
		// TODO Auto-generated method stub
		
	}

	private void darAltaVideojuego() {
		Videojuego videojuego = pedirDatosVideojuego();
		// int respuesta = ...........;
		/*switch (respuesta) {
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
		
		
		
		*/
		
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

	private int menu() {
		boolean correcto = false;
		int opcion = 0;
		while(!correcto) {
			System.out.println("              MENÚ PRINCIPAL");
			System.out.println("========================================");
			System.out.println("| 1. Registar Usuario                  |");
			System.out.println("| 2. Agregar Videojuego                |");
			System.out.println("| 3. Lista Videojuego                  |");
			System.out.println("| 4. Borrar Videojuego                  |");
			System.out.println("========================================");
			System.out.println("| 0. Salir del prograna                |");
			System.out.println("========================================");
			System.out.print("Selecciona una opción: ");
			opcion = sc.nextInt();
			if(opcion >= 0 && opcion <= 4) {
				correcto = true;
			}
		}		
		return opcion;
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
	private Videojuego pedirDatosVideojuego() {
		System.out.println("Intrduzca el nombre del Videojuego");
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
