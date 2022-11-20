package mx.uam.ayd.proyecto.presentacion.solicitarTramites;

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

    public void inicia(Agremiado agremiado) {
        if (servicioAgremiado.getAccesoATramites(agremiado)) {
            List<TipoTramite> tramites = servicioTipoTramite.findAll();
            ventana.ventanaSolicitarTramite(agremiado, tramites, this);
        } else {
            ventana.ventanaTramiteActivo(agremiado, this);   
        }

    }

    void adjuntarDocumentos(TipoTramite tipoTramite) {
        ventana.ventanaAdjuntarDocumentos(tipoTramite);
    }
    
}
