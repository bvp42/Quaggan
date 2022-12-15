package mx.uam.ayd.proyecto.presentacion.solicitarTramites;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import java.nio.file.Path;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudTramite;
import mx.uam.ayd.proyecto.negocio.modelo.TipoTramite;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;

@Component
public class VentanaSolicitarTramites extends Pantalla {

    private ControlSolicitarTramites control;
    private Agremiado agremiado;
    private TipoTramite tipoTramiteSeleccionado;
    private SolicitudTramite solicitudActiva;

    private GridBagConstraints gbc = new GridBagConstraints(), gbcPanel = new GridBagConstraints();
    private JPanel panelCentral, panelSolicitarTramite, panelHistorialTramites, panelTramiteActivo, panelCorreciones;
    private JLabel lblNorth, lblSeleccionarTramite, lblRequisitos, lblDocumentosSeleccionados,
            lblDocumentosSeleccionados_, lblNoSolicitud, lblNoSolicitud_, lblFechaSolicitud, lblFechaSolicitud_,
            lblTramiteSolicitado, lblTramiteSolicitado_, lblEstado, lblEstado_, lblTramitesCompletados,
            lblMotivoCorrecion, lblDetallesAdicionales;
    private JButton btnHistorialTramites, btnSiguienteSolicitarTramite, btnAdjuntarDocumentos,
            btnCancelarAdjuntarDocumetos, btnEnviarSolicitud, btnDescargarDocumentoTramite, btnAceptarTramite,
            btnSolicitarCorreccion, btnEnviarCorrecion, btnCancelarCorrecion;
    private JComboBox<String> comboBoxTramitesDisponibles, comboBoxMotivoCorrecion;
    private JTextField txtFieldDetallesAdicionales, txtFieldMotivoCorreccion;
    private JScrollPane scrollPaneMotivoCorrecion;
    private List<TipoTramite> listaTramites;
    private JList<String> jListRequerimientos;
    private JFileChooser chooser;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
    String[] listaVacia = { "Ningún documento seleccionado" };

    public VentanaSolicitarTramites() {
        setBounds(new Rectangle(100, 100, 500, 500));
        setLayout(new BorderLayout());

        /* LBL NORTH */
        lblNorth = new JLabel("SISTEMA DE GESTIÓN DE TRÁMITES SINDICALES");
        lblNorth.setFont(new Font("Arial", Font.BOLD, 15));
        lblNorth.setBorder(new EmptyBorder(10, 0, 10, 0));
        lblNorth.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblNorth, BorderLayout.NORTH);

        /* BTN SOUTH */
        btnHistorialTramites = new JButton("Historial de trámites finalizados");
        btnHistorialTramites.setFont(new Font("Arial", Font.BOLD, 15));
        btnHistorialTramites.setHorizontalAlignment(SwingConstants.CENTER);
        add(btnHistorialTramites, BorderLayout.SOUTH);

        /* PANEL CENTRAL */
        panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        add(panelCentral, BorderLayout.CENTER);

        gbcPanel.insets = new Insets(10, 10, 10, 10);

        /* -----PANEL SOLICITAR TRAMITE */
        panelSolicitarTramite = new JPanel();
        panelSolicitarTramite.setLayout(new GridBagLayout());
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        panelCentral.add(panelSolicitarTramite, gbcPanel);

        /* ---------ELEMENTOS SELECCIONAR TRAMITE */

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblSeleccionarTramite = new JLabel("Seleccionar trámite:");
        lblSeleccionarTramite.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelSolicitarTramite.add(lblSeleccionarTramite, gbc);

        comboBoxTramitesDisponibles = new JComboBox<String>();
        comboBoxTramitesDisponibles.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panelSolicitarTramite.add(comboBoxTramitesDisponibles, gbc);

        lblRequisitos = new JLabel("Documentos necesarios:");
        lblRequisitos.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelSolicitarTramite.add(lblRequisitos, gbc);

        jListRequerimientos = new JList<String>();
        jListRequerimientos.setFont(new Font("Arial", Font.PLAIN, 15));
        jListRequerimientos.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelSolicitarTramite.add(jListRequerimientos, gbc);

        btnSiguienteSolicitarTramite = new JButton("Siguiente");
        btnSiguienteSolicitarTramite.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelSolicitarTramite.add(btnSiguienteSolicitarTramite, gbc);

        /* ---------ELEMENTOS ADJUNTAR DOCUMENTOS */

        btnCancelarAdjuntarDocumetos = new JButton("Cancelar");
        btnCancelarAdjuntarDocumetos.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelSolicitarTramite.add(btnCancelarAdjuntarDocumetos, gbc);

        btnAdjuntarDocumentos = new JButton("Adjuntar documentos");
        btnAdjuntarDocumentos.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelSolicitarTramite.add(btnAdjuntarDocumentos, gbc);

        lblDocumentosSeleccionados = new JLabel("Documentos seleccionados:");
        lblDocumentosSeleccionados.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelSolicitarTramite.add(lblDocumentosSeleccionados, gbc);

        lblDocumentosSeleccionados_ = new JLabel("Ningún documento seleccionado.");
        lblDocumentosSeleccionados_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelSolicitarTramite.add(lblDocumentosSeleccionados_, gbc);

        btnEnviarSolicitud = new JButton("Enviar solicitud de trámite");
        btnEnviarSolicitud.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        btnEnviarSolicitud.setEnabled(false);
        panelSolicitarTramite.add(btnEnviarSolicitud, gbc);

        /* -----PANEL TRAMITE ACTIVO */
        panelTramiteActivo = new JPanel();
        panelTramiteActivo.setLayout(new GridBagLayout());
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 1;
        panelCentral.add(panelTramiteActivo, gbcPanel);

        /* ---------ELEMENTOS TRAMITE ACTIVO */
        lblNoSolicitud = new JLabel("No. de solicitud:");
        lblNoSolicitud.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblNoSolicitud, gbc);

        lblNoSolicitud_ = new JLabel("-");
        lblNoSolicitud_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblNoSolicitud_, gbc);

        lblFechaSolicitud = new JLabel("Fecha de registro:");
        lblFechaSolicitud.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblFechaSolicitud, gbc);

        lblFechaSolicitud_ = new JLabel("-");
        lblFechaSolicitud_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblFechaSolicitud_, gbc);

        lblTramiteSolicitado = new JLabel("Tramite solicitado:");
        lblTramiteSolicitado.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblTramiteSolicitado, gbc);

        lblTramiteSolicitado_ = new JLabel("-");
        lblTramiteSolicitado_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblTramiteSolicitado_, gbc);

        lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblEstado, gbc);

        lblEstado_ = new JLabel("-");
        lblEstado_.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(lblEstado_, gbc);

        btnDescargarDocumentoTramite = new JButton("Descargar documento de trámite");
        btnDescargarDocumentoTramite.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panelTramiteActivo.add(btnDescargarDocumentoTramite, gbc);

        btnAceptarTramite = new JButton("Aceptar trámite");
        btnAceptarTramite.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(btnAceptarTramite, gbc);

        btnSolicitarCorreccion = new JButton("Solicitar corrección");
        btnSolicitarCorreccion.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelTramiteActivo.add(btnSolicitarCorreccion, gbc);

        /* -----PANEL CORRECIONES */
        panelCorreciones = new JPanel();
        panelCorreciones.setLayout(new GridBagLayout());
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 2;
        panelCentral.add(panelCorreciones, gbcPanel);
        panelCorreciones.setVisible(false);

        /* ---------ELEMENTOS PANEL CORRECIONES */
        lblMotivoCorrecion = new JLabel("Motivo de la correción:");
        lblMotivoCorrecion.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelCorreciones.add(lblMotivoCorrecion, gbc);
        lblMotivoCorrecion.setVisible(false);

        comboBoxMotivoCorrecion = new JComboBox<>();
        comboBoxMotivoCorrecion.addItem("El documento adjunto corresponde a otra persona");
        comboBoxMotivoCorrecion.addItem("Los datos en el documento son erróneos");
        comboBoxMotivoCorrecion.addItem("No puedo abrir/visualizar el archivo");
        comboBoxMotivoCorrecion.addItem("Otro");
        comboBoxMotivoCorrecion.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panelCorreciones.add(comboBoxMotivoCorrecion, gbc);
        comboBoxMotivoCorrecion.setVisible(false);

        lblDetallesAdicionales = new JLabel("Detalles adicionales (opcional):");
        lblDetallesAdicionales.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelCorreciones.add(lblDetallesAdicionales, gbc);
        lblDetallesAdicionales.setVisible(false);

        txtFieldDetallesAdicionales = new JTextField();
        txtFieldDetallesAdicionales.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panelCorreciones.add(txtFieldDetallesAdicionales, gbc);
        txtFieldDetallesAdicionales.setVisible(false);

        btnCancelarCorrecion = new JButton("Cancelar");
        btnCancelarCorrecion.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelCorreciones.add(btnCancelarCorrecion, gbc);
        btnCancelarCorrecion.setVisible(false);

        btnEnviarCorrecion = new JButton("Enviar solicitud de correcion");
        btnEnviarCorrecion.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panelCorreciones.add(btnEnviarCorrecion, gbc);
        btnEnviarCorrecion.setVisible(false);

        txtFieldMotivoCorreccion = new JTextField();
        txtFieldMotivoCorreccion.setFont(new Font("Arial", Font.PLAIN, 15));
        txtFieldMotivoCorreccion.setEnabled(false);
        
        scrollPaneMotivoCorrecion = new JScrollPane(txtFieldMotivoCorreccion);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panelCorreciones.add(scrollPaneMotivoCorrecion, gbc);
        scrollPaneMotivoCorrecion.setPreferredSize(new Dimension(400, 50));

        /* -----PANEL HISTORIAL TRAMITES */
        panelHistorialTramites = new JPanel();
        panelHistorialTramites.setLayout(new GridBagLayout());
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 3;
        panelCentral.add(panelHistorialTramites, gbcPanel);
        panelHistorialTramites.setVisible(false);

        /* ---------ELEMENTOS PANEL HISTORIAL TRAMITES */
        lblTramitesCompletados = new JLabel();
        lblTramitesCompletados.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panelHistorialTramites.add(lblTramitesCompletados, gbc);

        /* ACTION LISTENERS */
        comboBoxTramitesDisponibles.addActionListener(e -> actualizarListaRequerimientos());

        btnSiguienteSolicitarTramite.addActionListener(e -> btnSiguienteSolicitarTramite());

        btnCancelarAdjuntarDocumetos.addActionListener(e -> btnCancelarAdjuntarDocumetos());

        btnAdjuntarDocumentos.addActionListener(e -> adjuntarDocumentos());

        btnEnviarSolicitud.addActionListener(e -> enviarSolicitud());

        btnDescargarDocumentoTramite.addActionListener(e -> descargarDocumentoTramite());

        btnAceptarTramite.addActionListener(e -> aceptarTramite());

        btnSolicitarCorreccion.addActionListener(e -> elegirMotivoCorrecion());

        btnCancelarCorrecion.addActionListener(e -> cancelarMotivoCorreccion());

        btnEnviarCorrecion.addActionListener(e -> enviarCorrecion());

        btnHistorialTramites.addActionListener(e -> historialTramites());

    }

    /**
     * Método que inicializa comunica a la ventana el agremiado que ha iniciado
     * sesión y el apuntador al control
     * 
     * @param agremiado Agremiado con sesión iniciada
     * @param control apuntador a ControlSolicitarTramites
     */
    void inicia(Agremiado agremiado, ControlSolicitarTramites control) {
        this.agremiado = agremiado;
        this.control = control;
    }

    /**
     * Actualiza el apuntador de agremiado luego de una actualiación
     * @param agremiado
     */
    void actualizarAgremiado (Agremiado agremiado) {
        this.agremiado = agremiado;
    }

    /**
     * Método que muestra la interfaz que permite solicitar un trámite
     * 
     * @param tramites  Lista que contiene los tipos de tramites que pueden
     *                  solicitarse
     */
    void ventanaSolicitarTramite(List<TipoTramite> tramites) {

        this.listaTramites = tramites;

        try {
            comboBoxTramitesDisponibles.removeAllItems();
        } catch (Exception e) {

        }

        for (TipoTramite tramite : listaTramites) {
            comboBoxTramitesDisponibles.addItem(tramite.getNombreTramite());
        }

        panelSolicitarTramite.setVisible(true);
        lblDocumentosSeleccionados.setVisible(false);
        lblDocumentosSeleccionados_.setVisible(false);
        btnCancelarAdjuntarDocumetos.setVisible(false);
        btnAdjuntarDocumentos.setVisible(false);
        btnEnviarSolicitud.setVisible(false);

        comboBoxTramitesDisponibles.setEnabled(true);
        btnSiguienteSolicitarTramite.setVisible(true);

        panelTramiteActivo.setVisible(false);

        setVisible(true);

    }

    /**
     * Método que muestra la interfaz con los datos de un trámite activo
     * 
     * @param solicitudActiva solicitud activa
     */
    void ventanaTramiteActivo(SolicitudTramite solicitudActiva) {

        this.solicitudActiva = solicitudActiva;
        panelSolicitarTramite.setVisible(false);
        panelTramiteActivo.setVisible(true);

        lblNoSolicitud_.setText(String.valueOf(solicitudActiva.getIdSolicitud()));
        lblFechaSolicitud_.setText(String.valueOf(solicitudActiva.getFechaSolicitud()).substring(0, 19));
        lblTramiteSolicitado_.setText(solicitudActiva.getTipoTramite().getNombreTramite());
        lblEstado_.setText(solicitudActiva.getEstado());

        switch (solicitudActiva.getEstado()) {
            case "Finalizado":
                btnDescargarDocumentoTramite.setVisible(true);
                btnAceptarTramite.setVisible(true);
                btnSolicitarCorreccion.setVisible(true);
                lblMotivoCorrecion.setVisible(false);
                comboBoxMotivoCorrecion.setVisible(false);
                comboBoxMotivoCorrecion.setEnabled(true);
                btnEnviarCorrecion.setVisible(false);
                btnCancelarCorrecion.setVisible(false);
                lblDetallesAdicionales.setVisible(false);
                txtFieldDetallesAdicionales.setVisible(false);
                txtFieldDetallesAdicionales.setText("");
                txtFieldDetallesAdicionales.setEnabled(true);
                scrollPaneMotivoCorrecion.setVisible(false);
                break;
            
            case "Erronea":
                btnDescargarDocumentoTramite.setVisible(false);
                btnAceptarTramite.setVisible(false);
                btnSolicitarCorreccion.setVisible(false);
                btnEnviarCorrecion.setVisible(false);
                btnCancelarCorrecion.setVisible(false);
                lblMotivoCorrecion.setVisible(true);
                lblDetallesAdicionales.setVisible(false);
                comboBoxMotivoCorrecion.setVisible(false);
                comboBoxMotivoCorrecion.setEnabled(false);
                txtFieldDetallesAdicionales.setVisible(false);
                txtFieldDetallesAdicionales.setText("");
                txtFieldDetallesAdicionales.setEnabled(false);
                scrollPaneMotivoCorrecion.setVisible(true);
                txtFieldMotivoCorreccion.setText(solicitudActiva.getMotivoCorrecion());
                break;

            default:
                btnDescargarDocumentoTramite.setVisible(false);
                btnAceptarTramite.setVisible(false);
                btnSolicitarCorreccion.setVisible(false);
                lblMotivoCorrecion.setVisible(false);
                comboBoxMotivoCorrecion.setVisible(false);
                btnEnviarCorrecion.setVisible(false);
                btnCancelarCorrecion.setVisible(false);
                lblDetallesAdicionales.setVisible(false);
                txtFieldDetallesAdicionales.setVisible(false);
                txtFieldDetallesAdicionales.setText("");
                scrollPaneMotivoCorrecion.setVisible(false);
                break;
        }

        setVisible(true);

    }

    /**
     * Actualiza los documentos requeridos en un trámite dependiendo de la opción
     * seleccionada en el ComboBox
     */
    void actualizarListaRequerimientos() {

        jListRequerimientos
                .setListData(listaTramites.get(comboBoxTramitesDisponibles.getSelectedIndex()).getRequerimientos());

    }

    /**
     * Método que actualiza la interfaz para mostrar los elementos necesarios para
     * adjuntar documentos a una solicitud de trámite
     * 
     * @param tipoTramite
     */
    void ventanaAdjuntarDocumentos(TipoTramite tipoTramite) {

        this.tipoTramiteSeleccionado = tipoTramite;

        panelSolicitarTramite.setVisible(true);
        lblSeleccionarTramite.setText("Tramite seleccionado:");
        comboBoxTramitesDisponibles.setEnabled(false);
        btnSiguienteSolicitarTramite.setVisible(false);
        btnCancelarAdjuntarDocumetos.setVisible(true);
        btnAdjuntarDocumentos.setVisible(true);
        lblDocumentosSeleccionados.setVisible(true);
        lblDocumentosSeleccionados_.setVisible(true);
        btnEnviarSolicitud.setVisible(true);
        btnEnviarSolicitud.setEnabled(false);

        JOptionPane.showMessageDialog(this, "Por favor, adjunte los documentos en el orden listado.");

        panelTramiteActivo.setVisible(false);

    }

    void btnSiguienteSolicitarTramite() {
        lblDocumentosSeleccionados_.setText("Ningún documento seleccionado.");
        control.adjuntarDocumentos(listaTramites.get(comboBoxTramitesDisponibles.getSelectedIndex()));
    }

    /**
     * Método invocado al dar clic en el botón Cancelar de la interfaz para adjuntar
     * documentos (reestablece la interfaz al estado anterior)
     */
    void btnCancelarAdjuntarDocumetos() {

        panelSolicitarTramite.setVisible(true);
        lblSeleccionarTramite.setText("Seleccionar Tramite:");
        comboBoxTramitesDisponibles.setEnabled(true);
        btnSiguienteSolicitarTramite.setVisible(true);
        btnCancelarAdjuntarDocumetos.setVisible(false);
        btnAdjuntarDocumentos.setVisible(false);
        lblDocumentosSeleccionados.setVisible(false);
        lblDocumentosSeleccionados_.setVisible(false);
        lblDocumentosSeleccionados_.setText("Ningún documento seleccionado.");
        btnEnviarSolicitud.setVisible(false);

        panelTramiteActivo.setVisible(false);
    }

    /**
     * Método que invoca una ventana que permite seleccionar los documentos pdf que
     * se desean adjuntar
     */
    void adjuntarDocumentos() {

        chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);

        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            String cadena = "";
            if (chooser.getSelectedFiles().length > 1) {
                cadena = chooser.getSelectedFiles().length + " elementos seleccionados";
            } else {
                cadena = "1 elemento seleccionado";
            }

            lblDocumentosSeleccionados_.setText(cadena);
            btnEnviarSolicitud.setEnabled(true);

        }

    }

    /**
     * Método que comunica al control los datos necesarios para registrar la
     * solicitud de trámite
     */
    void enviarSolicitud() {

        int opcionSeleccionada = JOptionPane.showConfirmDialog(this,
                "¿Esta seguro de enviar esta solicitud de trámite? \n Recuerde que solo puede tener una solicitud de trámite activa",
                "Confirmar selección", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcionSeleccionada == 0) {

            try {

                Path[] listaPaths = new Path[chooser.getSelectedFiles().length];

                for (int i = 0; i < chooser.getSelectedFiles().length; i++) {
                    listaPaths[i] = chooser.getSelectedFiles()[i].toPath();
                }

                control.enviarSolicitud(tipoTramiteSeleccionado, listaPaths, agremiado);

            } catch (Exception e) {
                if (chooser.getSelectedFiles().length > 1) {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un error, seleccione nuevamente los documentos",
                            "Error al adjuntar los documentos", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un error, seleccione nuevamente el documento",
                            "Error al adjuntar el documento", JOptionPane.ERROR_MESSAGE);
                }

            }

        }

    }

    /**
     * Método que invoca una ventana para elegir la ruta donde el agremiado desea
     * guardar el documento de trámite finalizado
     */
    void descargarDocumentoTramite() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Seleccione una ruta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File path = new File(
                    chooser.getSelectedFile() + "/Solicitud" + String.valueOf(solicitudActiva.getIdSolicitud())
                            + solicitudActiva.getTipoTramite().getNombreTramite() + ".pdf");

            try (FileOutputStream out = new FileOutputStream(path)) {
                out.write(solicitudActiva.getDocumentoTramite().getArchivo());
                out.close();

            } catch (Exception e_) {
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error, selecciona una nueva ruta",
                        "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    void aceptarTramite() {
        int opcionSeleccionada = JOptionPane.showConfirmDialog(this,
                "¿Esta seguro de dar por finalizado este trámite? \n Una vez acepte su documento, no podrá solicitar correción alguna",
                "Confirmar selección", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcionSeleccionada == 0) {

            control.documentoAceptado(agremiado);

        }

    }

    void elegirMotivoCorrecion() {
        panelCorreciones.setVisible(true);
        btnAceptarTramite.setVisible(false);
        btnSolicitarCorreccion.setVisible(false);
        lblMotivoCorrecion.setVisible(true);
        comboBoxMotivoCorrecion.setVisible(true);
        comboBoxMotivoCorrecion.setEnabled(true);
        lblDetallesAdicionales.setVisible(true);
        txtFieldDetallesAdicionales.setVisible(true);
        txtFieldDetallesAdicionales.setEnabled(true);
        btnEnviarCorrecion.setVisible(true);
        btnCancelarCorrecion.setVisible(true);
        txtFieldDetallesAdicionales.setText("");
    }

    void cancelarMotivoCorreccion() {
        panelCorreciones.setVisible(false);
        btnAceptarTramite.setVisible(true);
        btnSolicitarCorreccion.setVisible(true);
        lblMotivoCorrecion.setVisible(false);
        comboBoxMotivoCorrecion.setVisible(false);
        lblDetallesAdicionales.setVisible(false);
        txtFieldDetallesAdicionales.setVisible(false);
        btnEnviarCorrecion.setVisible(false);
        btnCancelarCorrecion.setVisible(false);
        txtFieldDetallesAdicionales.setText("");
    }

    void enviarCorrecion() {
        int opcionSeleccionada = JOptionPane.showConfirmDialog(this,
                "¿Esta seguro de la información seleccionada para realizar la corrección de su trámite?",
                "Confirmar selección", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcionSeleccionada == 0) {

            if (txtFieldDetallesAdicionales.getText().length() > 200) {
                JOptionPane.showMessageDialog(this, "Los detalles adicionales no pueden exceder los 200 caracteres",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            String motivoCorrecion = comboBoxMotivoCorrecion.getSelectedItem() + ". "
                    + txtFieldDetallesAdicionales.getText();
            control.correccionSolicitada(this.agremiado, motivoCorrecion);

        }
    }

    void historialTramites() {

        panelHistorialTramites.setVisible(!panelHistorialTramites.isVisible());
        lblTramitesCompletados
                .setText("USTED TIENE " + control.getTramitesCompletados(this.agremiado) + " TRAMITES ACEPTADOS");
    }

}
