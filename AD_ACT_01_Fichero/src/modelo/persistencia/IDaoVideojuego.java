package modelo.persistencia;

import java.util.ArrayList;

import modelo.entidad.Videojuego;

public interface IDaoVideojuego {

	void registrar(Videojuego vj) throws Exception;

	ArrayList<Videojuego> getListaVideojuegos() throws Exception;

	boolean borrarVideojuego(Videojuego videojuegoABorrar, boolean borrarTodos) throws Exception;
	
}
