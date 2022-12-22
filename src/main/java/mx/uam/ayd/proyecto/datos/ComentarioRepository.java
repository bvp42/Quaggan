package mx.uam.ayd.proyecto.datos;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;

/**
* Clase ComentarioRepository que sirve para recuperar todos los comentarios
* @author Ivan Omar
* @since 19/12/2022
*/
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
	
	public List<Comentario> findAll();

}