package mx.uam.ayd.proyecto.presentacion.Login;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Frame;
import java.awt.GridBagConstraints;

import mx.uam.ayd.proyecto.presentacion.agendarCita.VentanaAgendarCita;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import mx.uam.ayd.proyecto.presentacion.principal.ControlPrincipal;
import mx.uam.ayd.proyecto.presentacion.principal.VentanaInicio;
import mx.uam.ayd.proyecto.presentacion.Login.VistaLogin;;

public class RecuperarClave extends JFrame {
    
    private JButton volver;
    private JButton enviar;
    private JLabel olvidaste;
    private JLabel recuperar;
    private JLabel correo;
    private JLabel recuperarClave;
    private JTextField correoEscribe;
    String usuario;
    String password;

    public RecuperarClave() {
        super();
        this.setSize(500, 500); // (Ancho, Largo) Tamaño de la ventana
        this.dispose();
		this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Recuperar Contraseña");
        StarLabels();
        
        
        
    }

    public void StarLabels() {

        GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 71, 156, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{49, 0, 69, 0, 97, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

        recuperarClave = new JLabel("¿Olvidaste tu contraseña? Escribe tu correo para recuperarla");
		GridBagConstraints gbc_lblRecuperar = new GridBagConstraints();
		gbc_lblRecuperar.gridwidth = 3;
		gbc_lblRecuperar.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecuperar.gridx = 1;
		gbc_lblRecuperar.gridy = 0;
		this.add(recuperarClave, gbc_lblRecuperar);

        correo = new JLabel("Correo");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.gridwidth = 1;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 1;
		gbc_lblCorreo.gridy = 1;
		add(correo, gbc_lblCorreo);

        correoEscribe = new JTextField(16);
		GridBagConstraints gbcTfCampo = new GridBagConstraints();
		gbcTfCampo.gridwidth = 1;
		gbcTfCampo.insets = new Insets(0, 0, 5, 5);
		gbcTfCampo.gridx = 2;
		gbcTfCampo.gridy = 1;
		add(correoEscribe,gbcTfCampo);

        enviar = new JButton("Enviar");
        GridBagConstraints gbcEnviar = new GridBagConstraints();
        gbcEnviar.gridwidth = 1;
        gbcEnviar.insets = new Insets(10, 200, 5, 5);
        gbcEnviar.gridx = 2;
        gbcEnviar.gridy = 5;
        add(enviar,gbcEnviar);

        enviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String correo = correoEscribe.getText();
				if(correo.equals("")){
					JOptionPane.showMessageDialog(null, "Escriba su correo","Campo vacío",
					JOptionPane.WARNING_MESSAGE);
					correoEscribe.requestFocus();
				}else{

					JOptionPane.showMessageDialog(null, "Su contraseña fue enviada a su correo");
                    RecuperarClave.this.dispose(); // Cierra la ventana
				}
				correoEscribe.setText("");
			}
		});
				
        volver = new JButton("Volver");
        GridBagConstraints gbcVolver = new GridBagConstraints();
        gbcVolver.gridwidth = 1;
        gbcVolver.insets = new Insets(10, 0, 5, 5);
        gbcVolver.gridx = 2;
        gbcVolver.gridy = 5;
        add(volver,gbcVolver);
        volver.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            RecuperarClave.this.dispose(); // Cierra la ventana
            // setVisible(false);
        }
    });

    }
    
    

}
