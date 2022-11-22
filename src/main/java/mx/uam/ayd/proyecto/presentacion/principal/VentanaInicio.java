package mx.uam.ayd.proyecto.presentacion.principal;

import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import org.springframework.stereotype.Component;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class VentanaInicio extends Pantalla {
	
	private ControlPrincipal controlPrincipal;
	private JLabel lblSesion;
	private JButton btnCerraSesion;
	private JButton btnIniciarSesion;

	public VentanaInicio() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 71, 156, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{49, 0, 69, 0, 97, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblBienvenida = new JLabel("Bienvenido al sistema de administración SISGTS");
		GridBagConstraints gbc_lblBienvenida = new GridBagConstraints();
		gbc_lblBienvenida.gridwidth = 3;
		gbc_lblBienvenida.insets = new Insets(0, 0, 5, 5);
		gbc_lblBienvenida.gridx = 1;
		gbc_lblBienvenida.gridy = 1;
		add(lblBienvenida, gbc_lblBienvenida);
		
		lblSesion = new JLabel("Usted no ha iniciado sesión");
		GridBagConstraints gbc_lblSesion = new GridBagConstraints();
		gbc_lblSesion.gridwidth = 3;
		gbc_lblSesion.insets = new Insets(0, 0, 5, 5);
		gbc_lblSesion.gridx = 1;
		gbc_lblSesion.gridy = 3;
		add(lblSesion, gbc_lblSesion);
		
		btnIniciarSesion= new JButton("Iniciar sesión");
		btnIniciarSesion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controlPrincipal.inicia();
			}
		});
		GridBagConstraints gbc_btnIniciarSesion = new GridBagConstraints();
		gbc_btnIniciarSesion.gridwidth = 3;
		gbc_btnIniciarSesion.insets = new Insets(0, 0, 5, 5);
		gbc_btnIniciarSesion.gridx = 1;
		gbc_btnIniciarSesion.gridy = 5;
		add(btnIniciarSesion, gbc_btnIniciarSesion);

		
		btnCerraSesion= new JButton("Cerrar sesión");
		btnCerraSesion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controlPrincipal.cerrarSesion();
				lblSesion.setText("Sesión cerrada");
				btnCerraSesion.setVisible(false);
				btnIniciarSesion.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnCerrarSesion = new GridBagConstraints();
		gbc_btnCerrarSesion.gridwidth = 3;
		gbc_btnCerrarSesion.insets = new Insets(0, 0, 5, 5);
		gbc_btnCerrarSesion.gridx = 1;
		gbc_btnCerrarSesion.gridy = 5;
		add(btnCerraSesion, gbc_btnCerrarSesion);
		btnCerraSesion.setVisible(false);
	}
	
	public void muestra(ControlPrincipal controlPrincipal) {
		this.controlPrincipal = controlPrincipal;

		setVisible(true);
	}
	//Muestra que rol ha iniciado sesion
	public void ActualizaVentanaInicio(String rol){
		lblSesion.setText("Ustes ha iniciado sesión como "+ rol);
		btnCerraSesion.setVisible(true);
		btnIniciarSesion.setVisible(false);
	}
}
