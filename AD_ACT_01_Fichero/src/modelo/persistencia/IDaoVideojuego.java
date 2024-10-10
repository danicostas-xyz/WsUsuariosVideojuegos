package modelo.persistencia;

import modelo.entidad.Videojuego;

public interface IDaoVideojuego {

	void registrar(Videojuego vj);

	Videojuego getListaVideojuegos();

	boolean borrarVideojuego(Videojuego vj);
}
