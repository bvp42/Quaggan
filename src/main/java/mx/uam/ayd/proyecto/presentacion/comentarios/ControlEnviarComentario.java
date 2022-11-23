package mx.uam.ayd.proyecto.presentacion.comentarios;

import mx.uam.ayd.proyecto.negocio.ServicioCita;
import mx.uam.ayd.proyecto.negocio.modelo.Cita;
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
    private ServicioCita servicioCita;
     
    
    @Autowired
    private VentanaEnviarComentario ventanaEnviarComentario;
    


    /**
     * Inicia el controlador cambiado tambien
     */
    public void inicia(){
    	ventanaEnviarComentario.muestra(this);
    }

    /**
     * Devuelve una lista de citas según una lista de criterios
     *
     * @param filtros La lista de filtros
     *
     * @return La lista con las citas que coincidieron con todos los filtros
      */
    public List<Cita> getCitas(List<Filtro> filtros){
        return servicioCita.getCitas(filtros);
    }

    
}
