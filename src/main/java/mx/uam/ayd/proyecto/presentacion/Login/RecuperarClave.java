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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.Properties;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import mx.uam.ayd.proyecto.presentacion.agendarCita.VentanaAgendarCita;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import mx.uam.ayd.proyecto.presentacion.principal.ControlPrincipal;
import mx.uam.ayd.proyecto.presentacion.principal.VentanaInicio;
import mx.uam.ayd.proyecto.presentacion.Login.VistaLogin;

public class RecuperarClave extends JFrame {
    
    private JButton volver;
    private JButton enviar;
    private JLabel olvidaste;
    private JLabel recuperar;
    private JLabel correo;
    private JLabel recuperarClave;
    private JTextField correoEscribe;
    private final JPanel contentPane;
    private java.awt.Component componente;
    GridBagConstraints gbc_contenido;
    String usuario;
    String password;

    public RecuperarClave() {
        
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); Cierra todo el sistema
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{172, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

        setTitle("Recuperar Contraseña");
        this.setVisible(true);
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
			if(correo.equals("")) {
				JOptionPane.showMessageDialog(null, "Escriba su correo","Campo vacío", JOptionPane.WARNING_MESSAGE);
				correoEscribe.requestFocus();
			} else if (correo.equals("testerror80@gmail.com")) {
				/************************************************************************/
				String destinatario =  "testerror80@gmail.com"; // A quien le quieres escribir.
                    		String asunto = "Correo de prueba enviado desde Java";
                    		String cuerpo = "Esta es una prueba de correo...";
				// La dirección de correo de envío
                    		String remitente = "testerror80@gmail.com";
                   		// La clave de aplicación obtenida 
                    		String claveemail = "cecgysuracwdhyzk";
                    
                    		Properties props = System.getProperties();
                    		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
                    		props.put("mail.smtp.user", remitente);
                    		props.put("mail.smtp.clave", claveemail); // La clave de la cuenta
                    		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
                    		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
                    		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google
                    
                    		Session session = Session.getDefaultInstance(props);
                    		MimeMessage message = new MimeMessage(session);
                    
                    		try {
					message.setFrom(new InternetAddress(remitente));
                        		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario)); // Se podrían añadir varios de la misma manera
                       			message.setSubject(asunto);
                        		message.setText(cuerpo);
                        		Transport transport = session.getTransport("smtp");
                        		transport.connect("smtp.gmail.com", remitente, claveemail);
                        		transport.sendMessage(message, message.getAllRecipients());
					transport.close();
                    		} catch (MessagingException me) {
                        		me.printStackTrace();   // Si se produce un error
                    		}
                
                      
                    		/************************************************************************/
			    	JOptionPane.showMessageDialog(null, "Su contraseña fue enviada a su correo");
                    		RecuperarClave.this.dispose(); // Cierra la ventana
                	} else {
                    		JOptionPane.showMessageDialog(null, "Escriba un correo valido","Correo Invalido",
		    		JOptionPane.WARNING_MESSAGE);
			    	correoEscribe.requestFocus();
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
            
        }
    });

    }
    
}
