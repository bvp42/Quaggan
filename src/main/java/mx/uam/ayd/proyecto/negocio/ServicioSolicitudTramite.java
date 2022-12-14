package mx.uam.ayd.proyecto.negocio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.RepositoryAgremiado;
import mx.uam.ayd.proyecto.datos.RepositoryDocumento;
import mx.uam.ayd.proyecto.datos.RepositorySolicitudTramite;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Documento;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudTramite;
import mx.uam.ayd.proyecto.negocio.modelo.TipoTramite;

/**
 * Servicio principal para el ControlProcesarTramites
 * 
 * @author Adolfo Mejía
 */
@Service
public class ServicioSolicitudTramite {

    @Autowired
    private RepositorySolicitudTramite solicitudTramiteRepository;

    @Autowired
    private RepositoryAgremiado repositoryAgremiado;

    @Autowired
    private RepositoryDocumento repositoryDocumento;

    @Autowired
    private ServicioDocumento servicioDocumento;

    /**
     * Comunica al repositorio recuperar aquellas solicitudes de trámite cuyo estado
     * sea diferente de
     * "Finalizado"
     * 
     * @return una lista de solicitudes de trámite no finalizadas
     */
    public List<SolicitudTramite> findByEstadoNotFinalizado() {
        return solicitudTramiteRepository.findByEstadoNot("Finalizado");
    }

    /**
     * Comunica al repositorio recuperar aquellas solicitudes de trámite cuyo estado
     * sea "Finalizado"
     * 
     * @return una lista de solicitudes de trámite finalizadas
     */
    public List<SolicitudTramite> findByEstadoFinalizado() {
        return solicitudTramiteRepository.findByEstado("Finalizado");
    };

    /**
     * Indica al repositorio que debe actualizar o guardar la información de una
     * solicitud de trámite
     * 
     * @param solicitudSeleccionada la solicitud que se desea persistir
     * @throws IllegalArgumentException
     */
    public void save(SolicitudTramite solicitudSeleccionada) throws IllegalArgumentException {
        if (solicitudSeleccionada == null)
            throw new IllegalArgumentException("Argumento nulo no válido");

        solicitudTramiteRepository.save(solicitudSeleccionada);
    }

    /**
     * Actualiza el estado de una solicitud de trámite a "En progreso" para
     * posteriormente actualizar la información existente en la BD
     * 
     * @param solicitudSeleccionada la solicitud que se desea actualizar
     * @throws IllegalArgumentException
     * @return la solicitud actualizada
     */
    public SolicitudTramite aceptarDocumentos(SolicitudTramite solicitudSeleccionada) throws IllegalArgumentException {
        if (solicitudSeleccionada == null)
            throw new IllegalArgumentException("Argumento nulo no válido");

        try {
            solicitudSeleccionada.setEstado("En progreso");
            solicitudSeleccionada.setFechaAceptacion(new Date(System.currentTimeMillis()));
            save(solicitudSeleccionada);
            return solicitudSeleccionada;

        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Actualiza el estado de una solicitud de trámite a "Rechazada", así como el
     * motivo de dicho rechazo, posteriormente comunica a ServicioDocumento los
     * documentos anteriormente asociados a la solicitud para ser eliminados y
     * finalmente actualiza la información en la BD
     * 
     * @param solicitudSeleccionada la solicitud que se desea actualizar
     * @param motivoRechazo         motivo de rechazo seleccionada por el usuario
     * @throws IllegalArgumentException
     * @return la solicitud actualizada
     */
    public SolicitudTramite rechazarDocumentos(SolicitudTramite solicitudSeleccionada_, String motivoRechazo_)
            throws IllegalArgumentException {

        SolicitudTramite solicitudSeleccionada = solicitudSeleccionada_;
        String motivoRechazo = motivoRechazo_;

        if ((solicitudSeleccionada == null) || (motivoRechazo == null)
                || (solicitudSeleccionada.getRequisitos() == null))
            throw new IllegalArgumentException("Argumento nulo inválido");

        try {
            SolicitudTramite solicitudActualizada = servicioDocumento.eliminarDocumentos(solicitudSeleccionada);
            solicitudActualizada.setEstado("Rechazada");
            solicitudActualizada.setMotivoRechazo(motivoRechazo);
            solicitudTramiteRepository.save(solicitudActualizada);
            return solicitudActualizada;

        } catch (IllegalArgumentException e) {
            throw e;
        }

    }

    /**
     * Actualiza el estado de un trámite a "Finalizado", tambien adjunta el
     * documento del trámite solicitado mediante la creación de un Documento con
     * ayuda de la ruta del archivo seleccionada por el usuario, finalmente persiste
     * los datos en la BD
     * 
     * @param solicitudSeleccionada    la solicitud que se desea finalizar
     * @param pathDocTramiteFinalizado la ruta del documento del trámite solicitado
     * @throws IOException
     * @return la solicitud actualizada
     */
    public SolicitudTramite finalizarTramite(SolicitudTramite solicitudSeleccionada_, Path pathDocTramiteFinalizado_)
            throws IOException, IllegalArgumentException {

        SolicitudTramite solicitudSeleccionada = solicitudSeleccionada_;
        Path pathDocTramiteFinalizado = pathDocTramiteFinalizado_;
        if ((solicitudSeleccionada == null) || (pathDocTramiteFinalizado == null))
            throw new IllegalArgumentException("Argumento nulo no válido");

        try {

            String tipoDocumento = solicitudSeleccionada.getTipoTramite().getNombreTramite();
            Documento documentoTramiteFinalizado = servicioDocumento.creaDocumento(pathDocTramiteFinalizado,
                    tipoDocumento);
            solicitudSeleccionada.setEstado("Finalizado");
            solicitudSeleccionada.setDocumentoTramite(documentoTramiteFinalizado);
            solicitudSeleccionada.setFechaFinalizacion(new Date(System.currentTimeMillis()));
            solicitudTramiteRepository.save(solicitudSeleccionada);

            return solicitudSeleccionada;

        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Método que se encarga de crear todos los elementos necesarios necesarios para
     * la nueva solicitud a registrar y comunica a los repositorios los datos que
     * deben almacenarse. Devuelve al agremiado con sesión iniciada con su
     * información actualizada (Cancelado su acceso a trámites y ligada su solicitud
     * activa).
     * 
     * @param tipoTramiteSeleccionado El trámite que se desea solicitar
     * @param listaPaths              Lista que contiene las rutas del o los
     *                                archivos seleccionados que se han adjuntado
     *                                como requerimientos
     * @param agremiado               agremiado con sesión iniciada que solicta el
     *                                trámite
     * @return agremiado con sesión iniciada con datos actualizados
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public Agremiado enviarSolicitud(TipoTramite tipoTramiteSeleccionado, Path[] listaPaths, Agremiado agremiado)
            throws IOException, IllegalArgumentException, ArrayIndexOutOfBoundsException {

        if ((tipoTramiteSeleccionado == null) || (listaPaths == null) || (agremiado == null)) {
            throw new IllegalArgumentException("Argumento nulo no válido");
        }

        List<Documento> requisitos = new ArrayList<Documento>();

        try {

            for (int i = 0; i < listaPaths.length; i++) {
                Documento temp = servicioDocumento.creaDocumento(listaPaths[i],
                        tipoTramiteSeleccionado.getRequerimientos()[i]);
                repositoryDocumento.save(temp);
                requisitos.add(temp);
            }

            SolicitudTramite nuevaSolicitud = new SolicitudTramite();
            nuevaSolicitud.setEstado("Pendiente");
            nuevaSolicitud.setTipoTramite(tipoTramiteSeleccionado);
            nuevaSolicitud.setFechaSolicitud(new Date(System.currentTimeMillis()));
            nuevaSolicitud.setRequisitos(requisitos);
            nuevaSolicitud.setSolicitante(agremiado);

            solicitudTramiteRepository.save(nuevaSolicitud);

            agremiado.nuevaSolicitudRealizada(nuevaSolicitud);

            repositoryAgremiado.save(agremiado);

            return agremiado;

        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Método que se encarga de administrar las correciones que son solicitadas por
     * los agremiados tras recibir un documento de trámite que presente errores
     * 
     * @param agremiado       Agremiado con sesión iniciada quien ha solicitado una
     *                        revision de un trámite que considera erroneo
     * @param motivoCorrecion el motivo que ha seleccionado en la interfaz donde se
     *                        detalla los motivos por los cuales solicita la
     *                        correcion
     * @return el agremiado con los datos actualizados
     * @throws IllegalArgumentException
     */
    public Agremiado correccionSolicitada(Agremiado agremiado, String motivoCorrecion) throws IllegalArgumentException {
        if (agremiado == null || motivoCorrecion == null) {
            throw new IllegalArgumentException("Argumentos inválidos");
        }

        SolicitudTramite solicitudActiva = agremiado.getSolicitudActiva();

        solicitudActiva.setMotivoCorrecion(motivoCorrecion);
        solicitudActiva.setEstado("Erronea");
        solicitudActiva.setFechaCorrecion(new Date(System.currentTimeMillis()));

        solicitudTramiteRepository.save(solicitudActiva);

        agremiado.setSolicitudActiva(solicitudActiva);
        repositoryAgremiado.save(agremiado);

        return agremiado;

    }

    public List<SolicitudTramite> findBySolicitanteAndEstado(Agremiado agremiado, String estado) {
        return solicitudTramiteRepository.findBySolicitanteAndEstado(agremiado, estado);
    }

    /**
     * Método que recibe un agremiado con solicitud activa marcada como rechazada y
     * reasigna la lista de requerimientos por una nueva lista cuyos archivos estan
     * indicados por la lista de rutas
     * 
     * @param listaPaths lista con las rutas de los nuevos documentos a adjuntar
     * @param agremiado  agremiado con sesión iniciada y con solicitud activa
     *                   marcada como "Rechazada"
     * @return el agremiado con datos actualizados
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public Agremiado reenvioSolRechazada(Path[] listaPaths, Agremiado agremiado)
            throws IllegalArgumentException, IOException {

        if ((listaPaths == null) || (agremiado == null)) {
            throw new IllegalArgumentException("Argumento nulo no válido");
        }

        SolicitudTramite solicitudActiva = agremiado.getSolicitudActiva();
        List<Documento> requisitos = new ArrayList<Documento>();

        try {

            for (Documento documento : solicitudActiva.getRequisitos()) {
                repositoryDocumento.delete(documento);
            }

            for (int i = 0; i < listaPaths.length; i++) {
                Documento temp = servicioDocumento.creaDocumento(listaPaths[i],
                        solicitudActiva.getTipoTramite().getRequerimientos()[i]);
                repositoryDocumento.save(temp);
                requisitos.add(temp);
            }

            solicitudActiva.setRequisitos(requisitos);
            solicitudActiva.setEstado("Pendiente");

            solicitudTramiteRepository.save(solicitudActiva);

            agremiado.setSolicitudActiva(solicitudActiva);

            repositoryAgremiado.save(agremiado);

            return agremiado;

        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

}
