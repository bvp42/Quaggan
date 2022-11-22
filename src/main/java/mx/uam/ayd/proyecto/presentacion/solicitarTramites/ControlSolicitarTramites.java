package mx.uam.ayd.proyecto.presentacion.solicitarTramites;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioAgremiado;
import mx.uam.ayd.proyecto.negocio.ServicioSolicitudTramite;
import mx.uam.ayd.proyecto.negocio.ServicioTipoTramite;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.TipoTramite;

/**
 * Controlador para las HU relacionadas a las solicitudes de trámite
 * por parte de los agremiados
 * 
 * @author Adolfo Mejía
 */
@Component
public class ControlSolicitarTramites {

    @Autowired
    private ServicioAgremiado servicioAgremiado;

    @Autowired
    private ServicioTipoTramite servicioTipoTramite;

    @Autowired
    private ServicioSolicitudTramite servicioSolicitudTramite;

    @Autowired
    private VentanaSolicitarTramites ventana;

    /**
     * Método que inicia el control, este a su vez verifica si el agremiado que ha
     * iniciado sesión tiene acceso a realizar solicitudes de trámites, o si posee
     * un trámite activo, dependiendo de este resultado, se muestra la interfaz
     * correspondiente.
     * 
     * @param agremiado agremiado con sesión iniciada
     */
    public void inicia(Agremiado agremiado) {
        if (servicioAgremiado.getAccesoATramites(agremiado)) {
            List<TipoTramite> tramites = servicioTipoTramite.findAll();
            ventana.ventanaSolicitarTramite(agremiado, tramites, this);
        } else {
            ventana.ventanaTramiteActivo(agremiado, this);
        }

    }

    /**
     * Método que indica a la ventana la interfaz que debe desplegarse
     * 
     * @param tipoTramite trámite seleccionado por el agremiado con sesión iniciada
     */
    void adjuntarDocumentos(TipoTramite tipoTramite) {
        ventana.ventanaAdjuntarDocumentos(tipoTramite);
    }

    /**
     * Comunica al servicio correspondiente los datos que
     * 
     * @param tipoTramiteSeleccionado
     * @param listaPaths
     * @param agremiado
     * @throws IOException
     * @throws IllegalArgumentException
     */
    void enviarSolicitud(TipoTramite tipoTramiteSeleccionado, Path[] listaPaths, Agremiado agremiado)
            throws IOException, IllegalArgumentException {

        try {

            Agremiado agremiadoActualizado = servicioSolicitudTramite.enviarSolicitud(tipoTramiteSeleccionado,
                    listaPaths, agremiado);

            ventana.ventanaTramiteActivo(agremiadoActualizado, this);

        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }

    }

}
