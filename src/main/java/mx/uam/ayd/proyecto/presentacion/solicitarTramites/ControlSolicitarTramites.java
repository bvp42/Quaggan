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
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudTramite;
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

        ventana.inicia(agremiado, this);

        if (servicioAgremiado.getAccesoATramites(agremiado)) {
            List<TipoTramite> tramites = servicioTipoTramite.findAll();
            ventana.ventanaSolicitarTramite(tramites);
        } else {
            SolicitudTramite solicitudTramite = servicioAgremiado.getSolicitudActiva(agremiado);
            ventana.ventanaTramiteActivo(solicitudTramite);
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
            throws IOException, IllegalArgumentException, ArrayIndexOutOfBoundsException {

        try {

            Agremiado agremiadoActualizado = servicioSolicitudTramite.enviarSolicitud(tipoTramiteSeleccionado,
                    listaPaths, agremiado);
            SolicitudTramite solicitudActiva = servicioAgremiado.getSolicitudActiva(agremiadoActualizado);

            ventana.actualizarAgremiado(agremiadoActualizado);

            ventana.ventanaTramiteActivo(solicitudActiva);

        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }

    }

    /**
     * Método que comunica al servicio correspondiente sobre un trámite que ha sido
     * aceptado en su totalidad por el agremiado
     * 
     * @param agremiado Agremiado con sesión iniciada que ha aceptado un documento
     *                  de trámite
     * @throws IllegalArgumentException
     */
    void documentoAceptado(Agremiado agremiado) throws IllegalArgumentException {
        try {
            Agremiado agremiadoActualizado = servicioAgremiado.documentoAceptado(agremiado);
            List<TipoTramite> tramites = servicioTipoTramite.findAll();
            ventana.actualizarAgremiado(agremiadoActualizado);
            ventana.ventanaSolicitarTramite(tramites);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Método que comunica al servicio correspondiente una solicitud de correcion
     * sobre un trámite marcado anteriormente como finalizado
     * 
     * @param agremiado       Agremiado con sesión iniciada quien ha solicitado la
     *                        correcion
     * @param motivoCorrecion motivo por el que solicita la correccion, seleccionado
     *                        desde la interfaz correspondiente
     * @throws IllegalArgumentException
     */
    public void correccionSolicitada(Agremiado agremiado, String motivoCorrecion) throws IllegalArgumentException {
        try {
            Agremiado agremiadoActualizado = servicioSolicitudTramite.correccionSolicitada(agremiado, motivoCorrecion);
            ventana.actualizarAgremiado(agremiadoActualizado);
            ventana.ventanaTramiteActivo(agremiadoActualizado.getSolicitudActiva());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String getTramitesCompletados(Agremiado agremiado) {
        List<SolicitudTramite> tramitesCompletados = servicioSolicitudTramite.findBySolicitanteAndEstado(agremiado,
                "Aceptado");
        return String.valueOf(tramitesCompletados.size());
    }

}
