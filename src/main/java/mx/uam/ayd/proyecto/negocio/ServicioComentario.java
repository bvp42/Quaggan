package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uam.ayd.proyecto.datos.ComentarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;

@Service
public class ServicioComentario {
	@Autowired
	ComentarioRepository comentarioRepository;
	Comentario comentario;


	public boolean crearComentario(int Id, String fecha, String contenido) {
		Comentario coment = new Comentario();
		coment.setIdComentario(Id);
		coment.setFecha(fecha);
		coment.setContenido(contenido);
		coment.setRespuesta("");
		coment.setEstado("Sin Revisar");
		

		comentario = comentarioRepository.save(comentario);

		if (comentario.getIdComentario() > -1) {
			// Vamos a mantener el objeto de tipo aviso para retransmitir la infomacion
			comentario = coment;
			return true;
		} else {
			return false;
		}

	}

	public List<Comentario> recuperaTodos() {
		return comentarioRepository.findAll();
	}
	
}