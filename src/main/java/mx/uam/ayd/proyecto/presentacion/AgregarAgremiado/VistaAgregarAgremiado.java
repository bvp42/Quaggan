package mx.uam.ayd.proyecto.presentacion.AgregarAgremiado;
import org.checkerframework.checker.units.qual.g;
import org.springframework.stereotype.Component;

import antlr.debug.Event;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.awt.GridBagConstraints;

@Component
public class VistaAgregarAgremiado extends Pantalla{

    ControlAgregarAgremiado controlAgregarAgremiado;

    private final JLabel lblBienvenida;
    
    private JLabel lblClave;    
    private JTextField tfClave;
    private JLabel lblNombre;    
    private JTextField tfNombre;
    private JLabel lblApellidos;
    private JTextField tfApellidos;
    private JLabel lblCel;
    private JTextField tfCel;
    private JLabel lblCorreo;
    private JTextField tfCorreo;
    private JLabel lblDomicilio;
    private JTextField tfDomicilio;
    private JLabel lblPassword;
    private JTextField tfPassword;
    private JButton btnAceptar;
    private JButton btnBorrar;
    GridBagConstraints gbc = new GridBagConstraints(); 
    
	public VistaAgregarAgremiado(){

		setLayout(new GridBagLayout());
		
		lblBienvenida = new JLabel("Agregar un nuevo agremiado al SISGTS");
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.insets = new Insets(0,0,20,0);		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor =GridBagConstraints.CENTER;
		add(lblBienvenida,gbc);
		
		lblClave = new JLabel("Clave");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10,40,10,0);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor =GridBagConstraints.EAST;
		add(lblClave,gbc);
		
		tfClave = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(tfClave,gbc);

		lblNombre = new JLabel("Nombre");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(lblNombre,gbc);
		
		tfNombre = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(tfNombre,gbc);
		
		lblApellidos = new JLabel("Apellidos");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(lblApellidos,gbc);
		
		tfApellidos = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(tfApellidos,gbc);
		
		lblCel = new JLabel("Tel. Cel.");
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(lblCel,gbc);
		
		tfCel = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(tfCel,gbc);
		
		lblCorreo = new JLabel("Gmail");
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(lblCorreo,gbc);

		tfCorreo = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 5;
		add(tfCorreo,gbc);
		
		lblDomicilio = new JLabel("Domicilio");
		gbc.gridx = 0;
		gbc.gridy = 6;
		add(lblDomicilio,gbc);
		
		tfDomicilio = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 6;
		add(tfDomicilio,gbc);

		lblPassword = new JLabel("Contraseña");
		gbc.gridx = 0;
		gbc.gridy = 7;
		add(lblPassword,gbc);

		tfPassword = new JTextField();
		gbc.gridx = 2;
		gbc.gridy = 7;
		add(tfPassword,gbc);
		
		btnAceptar = new JButton("Aceptar");
		gbc.gridx = 0;
		gbc.gridy = 8;
		add(btnAceptar,gbc);
		
		btnBorrar = new JButton("Borrar");
		gbc.gridx = 2;
		gbc.gridy = 8;
		gbc.fill = GridBagConstraints.NONE;
		add(btnBorrar,gbc);

		btnBorrar.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				borrar();
			}
		});

		btnAceptar.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				String[] datos = new String[7];
				datos[0] = tfClave.getText();
				datos[1] = tfNombre.getText();
				datos[2] = tfApellidos.getText();
				datos[3] = tfCel.getText();
				datos[4] = tfCorreo.getText();
				datos[5] = tfDomicilio.getText();
				datos[6] = tfPassword.getText();
				System.out.println(datos[0]);
				if (datos[0].equals("")||datos[1].equals("")||datos[2].equals("")||datos[3].equals("")||datos[4].equals("")||
				datos[5].equals("")||datos[6].equals("")){
					JOptionPane.showMessageDialog(btnAceptar,"Lllene todos los campos","", 1); 
				}else{
					boolean seRegistro;
					try {
						seRegistro = controlAgregarAgremiado.AgregarAgremiado(datos);
						if (seRegistro){
							JOptionPane.showMessageDialog(null,"Su registrio fue exitoso", "Registro Agremiado", 1);	
							borrar();
						}
						
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
        
	}

	public void borrar(){
		tfClave.setText("");		
		tfNombre.setText("");
		tfApellidos.setText("");
		tfCel.setText("");
		tfCorreo.setText("");
		tfDomicilio.setText("");
		tfPassword.setText("");
		
	}
	public void muestra(ControlAgregarAgremiado controlAgregarAgremiado) {
		this.controlAgregarAgremiado = controlAgregarAgremiado;
		setVisible(true);
	}

}
