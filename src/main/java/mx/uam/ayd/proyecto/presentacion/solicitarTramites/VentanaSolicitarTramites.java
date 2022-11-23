package mx.uam.ayd.proyecto.presentacion.solicitarTramites;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.util.List;

import java.nio.file.Path;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.TipoTramite;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;

@Component
@Slf4j
public class VentanaSolicitarTramites extends Pantalla {

    private ControlSolicitarTramites control;
    private Agremiado agremiado;
    private TipoTramite tipoTramiteSeleccionado;

    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel panelCentral, panelSolicitarTramite, panelTramiteActivo;
    private JLabel lblNorth, lblSeleccionarTramite, lblRequisitos, lblDocumentosSeleccionados,
            lblDocumentosSeleccionados_;
    private JButton btnSiguienteSolicitarTramite, btnAdjuntarDocumentos, btnCancelarAdjuntarDocumetos,
            btnEnviarSolicitud;
    private JComboBox<String> comboBoxTramitesDisponibles;
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

        /* PANEL CENTRAL */
        panelCentral = new JPanel();
        add(panelCentral, BorderLayout.CENTER);

        /* -----PANEL SOLICITAR TRAMITE */
        panelSolicitarTramite = new JPanel();
        panelSolicitarTramite.setLayout(new GridBagLayout());
        panelCentral.add(panelSolicitarTramite);

        /* ---------ELEMENTOS SELECCIONAR TRAMITE */

        gbc.insets = new Insets(10, 10, 10, 10);

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
        gbc.fill = GridBagConstraints.HORIZONTAL;
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
        JLabel lblTAtemp = new JLabel("VENTANA DE TRÁMITE ACTIVO POR IMPLEMENTAR");
        panelTramiteActivo.setBorder(new LineBorder(new Color(255, 0, 0), 3, true));
        panelTramiteActivo.add(lblTAtemp);
        panelCentral.add(panelTramiteActivo);

        /* ACTION LISTENERS */
        comboBoxTramitesDisponibles.addActionListener(e -> actualizarListaRequerimientos());

        btnSiguienteSolicitarTramite.addActionListener(
                e -> control.adjuntarDocumentos(listaTramites.get(comboBoxTramitesDisponibles.getSelectedIndex())));

        btnCancelarAdjuntarDocumetos.addActionListener(e -> btnCancelarAdjuntarDocumetos());

        btnAdjuntarDocumentos.addActionListener(e -> adjuntarDocumentos());

        btnEnviarSolicitud.addActionListener(e -> enviarSolicitud());

    }

    /**
     * Método que muestra la interfaz que permite solicitar un trámite
     * 
     * @param agremiado Agremiado con sesión iniciada
     * @param tramites  Lista que contiene los tipos de tramites que pueden
     *                  solicitarse
     * @param control   apuntardor a ControlSolicitarTramites
     */
    void ventanaSolicitarTramite(Agremiado agremiado, List<TipoTramite> tramites,
            ControlSolicitarTramites control) {

        this.agremiado = agremiado;
        this.control = control;
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

        panelTramiteActivo.setVisible(false);

        setVisible(true);

    }

    void ventanaTramiteActivo(Agremiado agremiado, ControlSolicitarTramites control) {

        this.agremiado = agremiado;
        this.control = control;
        panelSolicitarTramite.setVisible(false);
        panelTramiteActivo.setVisible(true);
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

        log.info("SIGUIENTE");
        JOptionPane.showMessageDialog(this, "Por favor, adjunte los documentos en el orden listado.");

        panelTramiteActivo.setVisible(false);

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

        log.info("CANCELAR");

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

                e.printStackTrace();

            }

        }

    }

}
