package modelo.persistencia;

import modelo.entidad.Usuario;

public interface IDaoUsuario {
	Usuario getByName(String nombre) throws Exception;

	void registrar(Usuario u) throws Exception;

}
