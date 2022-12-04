package mx.uam.ayd.proyecto.presentacion.Login;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import mx.uam.ayd.proyecto.presentacion.principal.ControlPrincipal;

@SuppressWarnings("serial")
@Component
public class VistaLogin extends Pantalla {
	
	@Autowired
	private ControlLogin control;

	ControlPrincipal controlPrincipal;
	
	
	private final JLabel lblBienvenida;
	private final JLabel lblUsuario;
	private final JLabel lblClave;
	private JButton btnLogin;
	private JTextField tfUsuario;
	private JPasswordField pfClave;
	private JComboBox cbRol;
	String usuario;
	String clave;

	public VistaLogin() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 71, 156, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{49, 0, 69, 0, 97, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblBienvenida = new JLabel("Bienvenido al sistema de administración SISGTS");
		GridBagConstraints gbc_lblBienvenida = new GridBagConstraints();
		gbc_lblBienvenida.gridwidth = 3;
		gbc_lblBienvenida.insets = new Insets(0, 0, 5, 5);
		gbc_lblBienvenida.gridx = 1;
		gbc_lblBienvenida.gridy = 0;
		this.add(lblBienvenida, gbc_lblBienvenida);
		
		lblUsuario = new JLabel("Usuario");
		GridBagConstraints gbc_lblUsusario = new GridBagConstraints();
		gbc_lblUsusario.gridwidth = 1;
		gbc_lblUsusario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsusario.gridx = 1;
		gbc_lblUsusario.gridy = 1;
		add(lblUsuario, gbc_lblUsusario);


		tfUsuario = new JTextField(16);
		GridBagConstraints gbcTfUsuario = new GridBagConstraints();
		gbcTfUsuario.gridwidth = 1;
		gbcTfUsuario.insets = new Insets(0, 0, 5, 5);
		gbcTfUsuario.gridx = 2;
		gbcTfUsuario.gridy = 1;
		add(tfUsuario,gbcTfUsuario);

		lblClave = new JLabel("Contraseña");
		GridBagConstraints gbcClave = new GridBagConstraints();
		gbcClave.gridwidth = 1;
		gbcClave.insets = new Insets(0, 0, 5, 5);
		gbcClave.gridx = 1;
		gbcClave.gridy = 2;
		add(lblClave,gbcClave);

		pfClave = new JPasswordField(16);
		GridBagConstraints gbcPfClave = new GridBagConstraints();
		gbcPfClave.gridwidth = 1;
		gbcPfClave.insets = new Insets(0, 0, 5, 5);
		gbcPfClave.gridx = 2;
		gbcPfClave.gridy = 2;
		//pfClave.setEchoChar((char)0);
		add(pfClave,gbcPfClave);

		cbRol = new JComboBox<>();
		GridBagConstraints gbcRol = new GridBagConstraints();
		gbcRol.gridwidth = 1;
		gbcRol.insets = new Insets(10,0,5,5);
		gbcRol.gridx = 2;
		gbcRol.gridy = 3;
		add(cbRol,gbcRol);

		cbRol.addItem("");
		cbRol.addItem("Agremiado");
		cbRol.addItem("Empleado");

		btnLogin = new JButton("Ingresar");
		GridBagConstraints gbcLogin = new GridBagConstraints();
		gbcLogin.gridwidth = 1;
		gbcLogin.insets = new Insets(10, 0, 5, 5);
		gbcLogin.gridx = 2;
		gbcLogin.gridy = 4;
		add(btnLogin,gbcLogin);

		tfUsuario.setText("Yanely");
		pfClave.setText("123");
		cbRol.setSelectedIndex(2);
	
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String usuario = tfUsuario.getText();
				String password = new String(pfClave.getPassword());
				int rol = cbRol.getSelectedIndex();
				if(usuario.equals("") || password.equals("") || rol == 0){
					JOptionPane.showMessageDialog(null, "Llene todos los campos","Campos vacios",
					JOptionPane.WARNING_MESSAGE);
					tfUsuario.requestFocus();
				}else{
					try {
						control.validaDatos(controlPrincipal,usuario,password,rol);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
				}
				tfUsuario.setText("");
				pfClave.setText("");
				cbRol.setSelectedIndex(0);
			}
		});
		
	}
	
	
	public void muestra(ControlPrincipal controlPrincipal) {
		this.controlPrincipal = controlPrincipal;
		setVisible(true);
	}
    
    
}
