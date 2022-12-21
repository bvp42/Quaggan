package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import mx.uam.ayd.proyecto.datos.ComentarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;

/**
* Clase ServicioComentarioTest que sirve para probar la introduccion de comentarios al repository
* @author Ivan Omar
* @since 20/12/2022
*/
@ExtendWith(MockitoExtension.class)
class ServicioComentarioTest {
	@Mock
	ComentarioRepository comentariosRepository;
	@Mock
	Comentario comentario;
	
	@InjectMocks
	ServicioComentario servicioComentario;
    
	/**
	* testRecuperaUsuarios: Prueba introducir 2 usuarios al repository
	* @param Ninguno No tiene parametros de entrada 
	* @return No tiene parametros de salida
	*/
	@Test
	void testRecuperaUsuarios() {
		// Prueba 1: lista vacia cuando no hay elementos
		List <Comentario> comentarios = servicioComentario.recuperaTodos();
		assertTrue(comentarios.isEmpty());

		// Prueba 2: lista con usuarios
		List <Comentario> lista = new LinkedList <> ();

		var comentario1 = new Comentario();
		comentario1.setIdComentario(1234);
		comentario1.setContenido("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse at erat ac quam consequat tempus.");
		comentario1.setFecha("2222-09-22");
        comentario1.setEstado("Sin Revisar");
        comentario1.setRespuesta("");
		lista.add(comentario1);

		var comentario2 = new Comentario();
		comentario2.setIdComentario(9907);
		comentario2.setContenido("Prueba 2");
		comentario2.setFecha("2222-09-22");
        comentario1.setEstado("Sin Revisar");
        comentario1.setRespuesta("");
		lista.add(comentario2);

		when(comentariosRepository.findAll()).thenReturn(lista);
		comentarios = servicioComentario.recuperaTodos();
		assertEquals(2,comentarios.size());
	}
}
