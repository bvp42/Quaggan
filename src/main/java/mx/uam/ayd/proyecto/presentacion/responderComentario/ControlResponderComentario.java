package mx.uam.ayd.proyecto.presentacion.responderComentario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioComentario;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;

@Component
public class ControlResponderComentario {

    @Autowired
    private ServicioComentario servicioComentario;

    @Autowired
    private VentanaResponderComentario ventanaResponderComentario;

    public void inicia() {
        List<Comentario> comentarios = servicioComentario.recuperaTodos();
        ventanaResponderComentario.muestra(comentarios);
    }
}
