package mx.uam.ayd.proyecto.presentacion.solicitarTramites;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.util.List;

import java.io.*;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.privatejgoodies.forms.layout.CellConstraints.Alignment;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudTramite;
import mx.uam.ayd.proyecto.negocio.modelo.TipoTramite;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;

@Component
@Slf4j
public class VentanaSolicitarTramites extends Pantalla {

    private ControlSolicitarTramites control;
    private Agremiado agremiado;

    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel panelCentral, panelSolicitarTramite, panelTramiteActivo;
    private JLabel lblNorth, lblSeleccionarTramite, lblRequisitos, lblDocumentosSeleccionados,
            lblDocumentosSeleccionados_;
    private JButton btnSiguienteSolicitarTramite, btnAdjuntarDocumentos, btnCancelarAdjuntarDocumetos,
            btnConfirmarSolicitud;
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
        panelSolicitarTramite.setBorder(new LineBorder(new Color(255, 255, 255), 3, true));
        // panelSolicitarTramite.setLayout(new GridLayout(6, 1, 5, 10));
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

        btnConfirmarSolicitud = new JButton("Confirmar solicitud de trámite");
        btnConfirmarSolicitud.setFont(new Font("Arial", Font.ITALIC, 15));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        btnConfirmarSolicitud.setEnabled(false);
        panelSolicitarTramite.add(btnConfirmarSolicitud, gbc);


        /* -----PANEL TRAMITE ACTIVO */

        panelTramiteActivo = new JPanel();
        JLabel lblTAtemp = new JLabel("VENTANA DE TRÁMITE ACTIVO");
        panelTramiteActivo.setBorder(new LineBorder(new Color(255, 0, 0), 3, true));
        panelTramiteActivo.add(lblTAtemp);
        panelCentral.add(panelTramiteActivo);

        /* ACTION LISTENERS */
        comboBoxTramitesDisponibles.addActionListener(e -> actualizarListaRequerimientos());

        btnSiguienteSolicitarTramite.addActionListener(
                e -> control.adjuntarDocumentos(listaTramites.get(comboBoxTramitesDisponibles.getSelectedIndex())));

        btnCancelarAdjuntarDocumetos.addActionListener(e -> btnCancelarAdjuntarDocumetos());

        btnAdjuntarDocumentos.addActionListener(e -> adjuntarDocumentos());

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
        btnConfirmarSolicitud.setVisible(false);

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
     * Actualiza los documentos requeridos en un trámte dependiendo de la opción
     * seleccionada en el ComboBox
     */
    void actualizarListaRequerimientos() {

        jListRequerimientos
                .setListData(listaTramites.get(comboBoxTramitesDisponibles.getSelectedIndex()).getRequerimientos());

    }

    void ventanaAdjuntarDocumentos(TipoTramite tipoTramite) {

        panelSolicitarTramite.setVisible(true);
        lblSeleccionarTramite.setText("Tramite seleccionado:");
        comboBoxTramitesDisponibles.setEnabled(false);
        btnSiguienteSolicitarTramite.setVisible(false);
        btnCancelarAdjuntarDocumetos.setVisible(true);
        btnAdjuntarDocumentos.setVisible(true);
        lblDocumentosSeleccionados.setVisible(true);
        lblDocumentosSeleccionados_.setVisible(true);
        btnConfirmarSolicitud.setVisible(true);
        btnConfirmarSolicitud.setEnabled(false);

        log.info("SIGUIENTE");

        panelTramiteActivo.setVisible(false);

    }

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
        btnConfirmarSolicitud.setVisible(false);

        log.info("CANCELAR");

        panelTramiteActivo.setVisible(false);
    }

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
            btnConfirmarSolicitud.setEnabled(true);

        }

    }

}
