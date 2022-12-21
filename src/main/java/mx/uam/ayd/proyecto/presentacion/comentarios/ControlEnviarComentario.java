package mx.uam.ayd.proyecto.presentacion.comentarios;

import mx.uam.ayd.proyecto.negocio.ServicioCita;
import mx.uam.ayd.proyecto.negocio.ServicioComentario;
import mx.uam.ayd.proyecto.negocio.modelo.Cita;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;
import mx.uam.ayd.proyecto.util.Filtro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controlador para la historia de usuario "Consultar citas" (HU-03)
 *
 * @author Antar Espadas
 */
@Component
public class ControlEnviarComentario {
    
     
    @Autowired
    private ServicioComentario servicioComentario;

    @Autowired
    private VentanaEnviarComentario ventanaEnviarComentario;
    


    /**
     * Inicia el controlador cambiado tambien
     */
    public void inicia(){
        List<Comentario> comentarios = servicioComentario.recuperaTodos();
    	ventanaEnviarComentario.muestra(comentarios);
    }
}
