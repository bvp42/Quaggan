package mx.uam.ayd.proyecto.presentacion.procesarTramites;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;

import java.io.*;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.Documento;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudTramite;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;

@Component
public class VentanaProcesarTramites extends Pantalla {

    private ControlProcesarTramites control;

    private GridBagConstraints gbc = new GridBagConstraints();
    private JLabel lblNorth, lblNo, lblNo_, lblFecha, lblFecha_, lblEstado, lblEstado_, lblTipo, lblTipo_,
            lblSolicitante, lblSolicitante_, lblDocumentos, lblDocumentos_, lblMotivoRechazo, lblArchivoAdjunto, lblPlaceHolder;
    private List<SolicitudTramite> solicitudes, solicitudesFinalizadas;
    private JList<String> listaSolicitudes;
    private JPanel panelCentral, panelDatosSolicitud;
    private JScrollPane barraDespl;
    private JButton btnDescargarDocumentos, btnSiguiente, btnAlternarVista, btnConfirmarRechazo, btnAdjuntarDocTramite,
            btnFinalizarTramite;
    private JRadioButton radioBtnAceptar, radioBtnRechazar;
    private List<Documento> documentosAdjuntos;
    private Path pathDocTramiteFinalizado;
    private SolicitudTramite solicitudSeleccionada;
    private JComboBox<String> comboBoxMotivosRechazo;
    private JFileChooser chooser;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");

    public VentanaProcesarTramites() {
        setBounds(new Rectangle(100, 100, 500, 500));
        setLayout(new BorderLayout());

        /* LBL NORTH */
        lblNorth = new JLabel("SISTEMA DE GESTIÓN DE TRÁMITES SINDICALES: SOLICITUDES DE TRÁMITE ACTIVAS");
        lblNorth.setFont(new Font("Arial", Font.PLAIN, 15));
        lblNorth.setBorder(new EmptyBorder(10, 0, 10, 0));
        lblNorth.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblNorth, BorderLayout.NORTH);

        /* BTN SOUTH */
        btnAlternarVista = new JButton("Ver trámites finalizados");
        add(btnAlternarVista, BorderLayout.SOUTH);

        /* PANEL CENTRAL */
        panelCentral = new JPanel();
        add(panelCentral, BorderLayout.CENTER);

        /* -----PANEL DATOS SOLICITUD */
        panelDatosSolicitud = new JPanel();
        panelDatosSolicitud.setLayout(new GridBagLayout());
        panelCentral.add(panelDatosSolicitud, BorderLayout.CENTER);

        /* ---------ELEMENTOS SOLICITUD */

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblNo = new JLabel("Número de solicitud:");
        lblNo.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblNo, gbc);

        lblNo_ = new JLabel();
        lblNo_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblNo_, gbc);

        lblFecha = new JLabel("Fecha de registro:");
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblFecha, gbc);

        lblFecha_ = new JLabel();
        lblFecha_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblFecha_, gbc);

        lblEstado = new JLabel("Estado de la solicitud:");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblEstado, gbc);

        lblEstado_ = new JLabel();
        lblEstado_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblEstado_, gbc);

        lblTipo = new JLabel("Trámite solicitado:");
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblTipo, gbc);

        lblTipo_ = new JLabel("Trámite solicitado:");
        lblTipo_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblTipo_, gbc);

        lblSolicitante = new JLabel("Solicitante:");
        lblSolicitante.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblSolicitante, gbc);

        lblSolicitante_ = new JLabel();
        lblSolicitante_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblSolicitante_, gbc);

        lblDocumentos = new JLabel("Documentos:");
        lblDocumentos.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblDocumentos, gbc);

        lblDocumentos_ = new JLabel("Documentos:");
        lblDocumentos_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblDocumentos_, gbc);

        btnDescargarDocumentos = new JButton("Descargar documentos");
        btnDescargarDocumentos.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(btnDescargarDocumentos, gbc);

        radioBtnAceptar = new JRadioButton("Aceptar documentos");
        radioBtnAceptar.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(radioBtnAceptar, gbc);

        radioBtnRechazar = new JRadioButton("Rechazar documentos");
        radioBtnRechazar.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(radioBtnRechazar, gbc);

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(btnSiguiente, gbc);

        lblMotivoRechazo = new JLabel("Motivo de rechazo: ");
        lblMotivoRechazo.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblMotivoRechazo, gbc);

        String[] opcionesComboBox = { "Archivos corruptos", "Archivos ilegibles",
                "Documentos no coincidentes con los requerimientos del trámite" };
        comboBoxMotivosRechazo = new JComboBox<String>(opcionesComboBox);
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panelDatosSolicitud.add(comboBoxMotivosRechazo, gbc);

        btnConfirmarRechazo = new JButton("Confirmar rechazo de solicitud");
        btnConfirmarRechazo.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(btnConfirmarRechazo, gbc);

        btnAdjuntarDocTramite = new JButton("Adjuntar documento de trámite");
        btnAdjuntarDocTramite.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(btnAdjuntarDocTramite, gbc);

        lblArchivoAdjunto = new JLabel();
        lblArchivoAdjunto.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblArchivoAdjunto, gbc);

        btnFinalizarTramite = new JButton("Finalizar trámite");
        btnFinalizarTramite.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(btnFinalizarTramite, gbc);

        lblPlaceHolder = new JLabel("VISTA POR IMPLEMENTAR");
        lblPlaceHolder.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelDatosSolicitud.add(lblPlaceHolder, gbc);
        lblPlaceHolder.setVisible(false);

        panelDatosSolicitud.setVisible(false);

        /* LISTA DE SOLICITUDES (WEST) */
        listaSolicitudes = new JList<String>();
        barraDespl = new JScrollPane(listaSolicitudes);
        add(barraDespl, BorderLayout.WEST);

        /* ACTION LISTENERS */

        listaSolicitudes.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                int index = listaSolicitudes.getSelectedIndex();
                SolicitudTramite solicitudSeleccionada;
                String vista = btnAlternarVista.getText();

                switch (vista) {
                    case "Ver trámites finalizados":
                        solicitudSeleccionada = solicitudes.get(index);
                        break;
                    default:
                        solicitudSeleccionada = solicitudesFinalizadas.get(index);
                        break;
                }

                control.actualizarVista(solicitudSeleccionada);

            }

        });

        btnDescargarDocumentos.addActionListener(e -> guardarDocumentos(solicitudSeleccionada));

        radioBtnAceptar.addActionListener(e -> radioBtnRechazar.setSelected(false));

        radioBtnRechazar.addActionListener(e -> radioBtnAceptar.setSelected(false));

        btnSiguiente.addActionListener(e -> control.procesarSolicitud(solicitudSeleccionada));

        btnConfirmarRechazo.addActionListener(e -> control.confirmarRechazo(solicitudSeleccionada));

        btnAdjuntarDocTramite.addActionListener(e -> adjuntarArchivo());

        btnFinalizarTramite.addActionListener(e -> finalizarTramite());

        btnAlternarVista.addActionListener(e -> control.alternarVista());

    }

    /**
     * Muestra la vista correspondiente al procesamiento de tramites
     * 
     * @param solicitudes_            lista de solicitudes de trámites pendientes
     * @param solicitudesFinalizadas_ lista de trámites finalizados
     * @param control                 apuntador a ControlProcesarTramites
     */
    void muestra(List<SolicitudTramite> solicitudes_, List<SolicitudTramite> solicitudesFinalizadas_,
            ControlProcesarTramites control) {

        this.control = control;
        this.solicitudes = solicitudes_;
        this.solicitudesFinalizadas = solicitudesFinalizadas_;

        String[] datosListaSolicitudes = new String[solicitudes.size()];

        int i = 0;
        for (SolicitudTramite solicitud : solicitudes) {
            datosListaSolicitudes[i] = solicitud.toString();
            i++;
        }

        listaSolicitudes.setListData(datosListaSolicitudes);
        setVisible(true);

        if (solicitudes.size() == 0) {
            JOptionPane.showMessageDialog(this, "No hay solicitudes de trámites activas", "",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

    void actualizarVista(SolicitudTramite solicitudSeleccionada) {

        this.solicitudSeleccionada = solicitudSeleccionada;
        panelDatosSolicitud.setVisible(true);

        lblNo_.setText(String.valueOf(solicitudSeleccionada.getIdSolicitud()));
        lblFecha_.setText(
                "Fecha de registro: " + String.valueOf(solicitudSeleccionada.getFechaSolicitud()).substring(0, 10));
        lblEstado_.setText(solicitudSeleccionada.getEstado());
        lblTipo_.setText(solicitudSeleccionada.getTipoTramite().getNombreTramite());
        lblSolicitante_.setText(solicitudSeleccionada.getSolicitante().getNombre() + " "
                + solicitudSeleccionada.getSolicitante().getApellidos() + " ("
                + solicitudSeleccionada.getSolicitante().getClave() + ")");

        String strDocumentos = "";
        try {
            documentosAdjuntos = solicitudSeleccionada.getRequisitos();

                for (Documento documento : documentosAdjuntos) {
                    strDocumentos += " " + documento.getTipoDocumento();
                }
        } catch (Exception e) {
            
        }

        switch (solicitudSeleccionada.getEstado()) {
            case "Pendiente":

                lblDocumentos_.setText(strDocumentos);
                lblDocumentos.setVisible(true);
                lblDocumentos_.setVisible(true);
                btnDescargarDocumentos.setVisible(true);
                radioBtnAceptar.setVisible(true); 
                radioBtnRechazar.setVisible(true); 
                radioBtnAceptar.setSelected(false);
                radioBtnRechazar.setSelected(false);
                btnSiguiente.setVisible(true);
                lblMotivoRechazo.setVisible(false);
                comboBoxMotivosRechazo.setVisible(false);
                btnConfirmarRechazo.setVisible(false);
                btnAdjuntarDocTramite.setVisible(false);
                lblArchivoAdjunto.setVisible(false);
                btnFinalizarTramite.setVisible(false);
                break;

            case "En progreso":
                this.pathDocTramiteFinalizado = null;
                lblDocumentos.setVisible(true);
                lblDocumentos_.setVisible(true);
                lblDocumentos_.setText(strDocumentos);
                radioBtnAceptar.setVisible(false);
                radioBtnRechazar.setVisible(false);
                lblDocumentos.setVisible(true);
                btnDescargarDocumentos.setVisible(true);
                btnSiguiente.setVisible(false);
                lblMotivoRechazo.setVisible(false);
                comboBoxMotivosRechazo.setVisible(false);
                btnConfirmarRechazo.setVisible(false);
                btnAdjuntarDocTramite.setVisible(true);
                lblArchivoAdjunto.setVisible(true);
                lblArchivoAdjunto.setText("Ningún documento seleccionado");
                btnFinalizarTramite.setVisible(true);
                btnFinalizarTramite.setEnabled(false);
                break;

            case "Rechazada":
                radioBtnAceptar.setVisible(false);
                radioBtnRechazar.setVisible(false);
                lblDocumentos.setVisible(false);
                btnDescargarDocumentos.setVisible(false);
                btnSiguiente.setVisible(false);
                lblMotivoRechazo.setVisible(true);
                comboBoxMotivosRechazo.setVisible(true);
                comboBoxMotivosRechazo.setSelectedItem(solicitudSeleccionada.getMotivoRechazo());
                comboBoxMotivosRechazo.setEnabled(false);
                btnConfirmarRechazo.setVisible(false);
                btnAdjuntarDocTramite.setVisible(false);
                lblArchivoAdjunto.setVisible(false);
                btnFinalizarTramite.setVisible(false);
                break;

            case "Finalizado":
                lblDocumentos.setVisible(false);
                btnDescargarDocumentos.setVisible(false);
                radioBtnAceptar.setVisible(false);
                radioBtnRechazar.setVisible(false);
                btnSiguiente.setVisible(false);
                lblMotivoRechazo.setVisible(false);
                comboBoxMotivosRechazo.setVisible(false);
                btnConfirmarRechazo.setVisible(false);
                btnAdjuntarDocTramite.setVisible(false);
                lblArchivoAdjunto.setVisible(false);
                btnFinalizarTramite.setVisible(false);

                break;

            case "Erronea":
                lblPlaceHolder.setVisible(true);
                break;

            default:
                panelDatosSolicitud.setVisible(false);
                break;
        }

    }

    /**
     * Abre una ventana que permite al usuario elegir la carpeta en la que desea
     * guardar los documentos adjuntos a una solicitud de trámites
     * 
     * @param solicitudSeleccionada solicitud seleccionada de la lista por el
     *                              usuario
     */
    void guardarDocumentos(SolicitudTramite solicitudSeleccionada) {

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Seleccione una ruta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            for (Documento documento : documentosAdjuntos) {
                File path = new File(chooser.getSelectedFile() + "/Solicitud"
                        + String.valueOf(solicitudSeleccionada.getIdSolicitud()) + documento.getTipoDocumento()
                        + ".pdf");

                try (FileOutputStream out = new FileOutputStream(path)) {
                    out.write(documento.getArchivo());
                    out.close();

                } catch (Exception e_) {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un error, selecciona una nueva ruta",
                            "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
                    break;
                }

            }

        }

    }

    /**
     * Comunica al control si marcar una solicitud pendiente como "En progreso" o
     * "Rechazada" acorde a la opción elegida por el usuario
     * 
     * @param solicitudSeleccionada solicitud seleccionada de la lista por el
     *                              usuario
     */
    void procesarSolicitud(SolicitudTramite solicitudSeleccionada) {

        if (radioBtnAceptar.isSelected()) {

            int opcionSeleccionada = JOptionPane.showConfirmDialog(this,
                    "¿Esta seguro de aceptar los documentos y marcar la solicitud como \"En progreso\"?",
                    "Confirmar selección", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcionSeleccionada == 0) {

                int index = solicitudes.indexOf(solicitudSeleccionada);
                solicitudes.remove(solicitudSeleccionada);
                SolicitudTramite solicitudActualizada = new SolicitudTramite();
                try {
                    solicitudActualizada = control.aceptarDocumentos(solicitudSeleccionada);
                } catch (Exception e_) {
                    JOptionPane.showMessageDialog(this, e_.getMessage(), "Ha ocurrido un error",
                            JOptionPane.ERROR_MESSAGE);
                }
                actualizarLista(index, solicitudActualizada);

            }

        } else if (radioBtnRechazar.isSelected()) {

            int opcionSeleccionada = JOptionPane.showConfirmDialog(this,
                    "¿Esta seguro de rechazar los documentos y marcar la solicitud como \"Rechazada\"?",
                    "Confirmar selección", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcionSeleccionada == 0) {

                btnDescargarDocumentos.setVisible(false);
                btnSiguiente.setVisible(false);
                radioBtnAceptar.setVisible(false);
                radioBtnRechazar.setVisible(false);
                lblMotivoRechazo.setVisible(true);
                comboBoxMotivosRechazo.setSelectedIndex(0);
                comboBoxMotivosRechazo.setVisible(true);
                comboBoxMotivosRechazo.setEnabled(true);
                btnConfirmarRechazo.setVisible(true);

            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "Seleccione 'Aceptar documentos' o 'Rechazar documentos' antes de proceder", "Operación no válida",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Toma el motivo de rechazo seleccionado por el usuario y lo comunica al
     * control junto con la solicitud a actualizar
     * 
     * @param solicitudSeleccionada solicitud de la lista seleccionada por el
     *                              usuario
     */
    void confirmarRechazo(SolicitudTramite solicitudSeleccionada) {

        String motivoRechazo = comboBoxMotivosRechazo.getSelectedItem().toString();
        int index = solicitudes.indexOf(solicitudSeleccionada);
        solicitudes.remove(solicitudSeleccionada);
        try {
            SolicitudTramite solicitudActualizada = control.rechazarDocumentos(solicitudSeleccionada, motivoRechazo);
            actualizarLista(index, solicitudActualizada);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ha ocurrido un error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Actualiza los elementos desplegados en la lista de trámites tras actualizar
     * el estado de una solicitud seleccionada
     * 
     * @param index                indica la posición a la que debe ser reinsertado
     *                             el elemento con estado actualizado, si es -1
     *                             indica que la solicitud fue marcadac como
     *                             finalizada y debe ser agregada al final de la
     *                             lista de solicitudes finalizadas
     * @param solicitudActualizada solicitud con la información actualizada
     */
    void actualizarLista(int index, SolicitudTramite solicitudActualizada) {

        if (index == -1) {

            String[] datosListaSolicitudes = new String[solicitudes.size()];
            int i = 0;
            for (SolicitudTramite solicitud : solicitudes) {
                datosListaSolicitudes[i++] = solicitud.toString();
            }

            try {
                listaSolicitudes.setListData(datosListaSolicitudes);
            } catch (IndexOutOfBoundsException e) {
                control.actualizarVista(solicitudActualizada);
            }

            this.solicitudesFinalizadas.add(solicitudActualizada);

        } else {

            this.solicitudes.add(index, solicitudActualizada);
            String[] datosListaSolicitudes = new String[solicitudes.size()];
            int i = 0;
            for (SolicitudTramite solicitud : solicitudes) {
                datosListaSolicitudes[i++] = solicitud.toString();
            }

            try {
                listaSolicitudes.setListData(datosListaSolicitudes);
            } catch (IndexOutOfBoundsException e) {
                listaSolicitudes.setSelectedIndex(index);
                this.solicitudSeleccionada = solicitudActualizada;
            }
        }

    }

    /**
     * Abre una ventana para elegir el archivo pdf que debe adjuntarse a un trámite
     * va a darse por finalizado
     */
    void adjuntarArchivo() {

        chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            lblArchivoAdjunto.setText(chooser.getSelectedFile().getName());
            this.pathDocTramiteFinalizado = chooser.getSelectedFile().toPath();
            btnFinalizarTramite.setEnabled(true);

        }

    }

    /**
     * Despliega un cuadro de diálogo solicitanto la confirmación sobre la
     * finalización de un trámite, de ser el caso, comunica la desición al control
     */
    void finalizarTramite() {

        int opcionSeleccionada = JOptionPane.showConfirmDialog(this,
                "¿Esta seguro de adjuntar el documento y marcar el trámite como \"Finalizado\"?",
                "Confirmar selección", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcionSeleccionada == 0) {
            solicitudes.remove(solicitudSeleccionada);
            SolicitudTramite solicitudActualizada;
            try {
                solicitudActualizada = control.finalizarTramite(solicitudSeleccionada,
                        pathDocTramiteFinalizado);
                actualizarLista(-1, solicitudActualizada);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error, seleccione nuevamente el documento",
                        "Error al adjuntar documento", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    /**
     * Alterna las solicitudes desplegadas, entre mostrar las solicitudes no
     * finalizadas a las solicitudes finalizadas o viceversa.
     */
    void alternarVista() {

        if (btnAlternarVista.getText() == "Ver trámites finalizados") {

            panelDatosSolicitud.setVisible(false);
            btnAlternarVista.setText("Ver solicitudes de trámites activas");
            String[] datosListaSolicitudesFinalizadas = new String[solicitudesFinalizadas.size()];
            int i = 0;
            for (SolicitudTramite solicitudFinalizada : solicitudesFinalizadas) {
                datosListaSolicitudesFinalizadas[i++] = solicitudFinalizada.toString();
            }

            try {
                listaSolicitudes.setListData(datosListaSolicitudesFinalizadas);
                if (solicitudesFinalizadas.size() == 0) {
                    JOptionPane.showMessageDialog(this, "No hay registro de trámites finalizados", "",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IndexOutOfBoundsException e) {
                if (solicitudesFinalizadas.size() == 0) {
                    JOptionPane.showMessageDialog(this, "No hay registro de trámites finalizados", "",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }

        } else {

            panelDatosSolicitud.setVisible(false);
            btnAlternarVista.setText("Ver trámites finalizados");
            String[] datosListaSolicitudes = new String[solicitudes.size()];
            int i = 0;
            for (SolicitudTramite solicitud : solicitudes) {
                datosListaSolicitudes[i++] = solicitud.toString();
            }

            try {
                listaSolicitudes.setListData(datosListaSolicitudes);
                if (solicitudes.size() == 0) {
                    JOptionPane.showMessageDialog(this, "No hay solicitudes de trámites activas", "",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IndexOutOfBoundsException e) {
                if (solicitudes.size() == 0) {
                    JOptionPane.showMessageDialog(this, "No hay solicitudes de trámites activas", "",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }

        }

    }

}
