package mx.uam.ayd.proyecto.presentacion.AgregarAgremiado;

import org.springframework.stereotype.Component;
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
import mx.uam.ayd.proyecto.util.validarCampo;

@Component
public class VistaAgregarAgremiado extends Pantalla{

	ControlAgregarAgremiado controlAgregarAgremiado;	
	private final JLabel lblBienvenida;
	private JLabel lblClave;
	private validarCampo tfClave;    
    private JLabel lblNombre;    
    private JTextField tfNombre;
    private JLabel lblApellidos;
    private JTextField tfApellidos;
    private JLabel lblCel;
    private validarCampo tfCel;
    private JLabel lblCorreo;
    private validarCampo tfCorreo;
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
		//Agrega la etiqueta para la clave
		lblClave = new JLabel("Clave");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10,40,10,0);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor =GridBagConstraints.EAST;
		add(lblClave,gbc);
		//Valida con una expresión regular la clave. Es una cadena de 19 números y mayúsculas, seguda de un punto y 9 números 
		tfClave = new validarCampo("[0-9A-Z]{19}+.+[0-9]{6}");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(tfClave,gbc);
		tfClave.setToolTipText("Solo números y letras mayúsculas");
		//Agrega la etiqueta para el nombre
		lblNombre = new JLabel("Nombre");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(lblNombre,gbc);
		
		tfNombre = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(tfNombre,gbc);
		//Agrega la etiqueta para los apellidos
		lblApellidos = new JLabel("Apellidos");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(lblApellidos,gbc);
		
		tfApellidos = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(tfApellidos,gbc);
		//Agrega la etiqueta para el número de celular 
		lblCel = new JLabel("Tel. Cel.");
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(lblCel,gbc);
		// Valida que solo sean numeros de 10 dígitos
		tfCel = new validarCampo("[0-9]{10}");
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(tfCel,gbc);
		//Agrega la etiqueta para el correo 
		lblCorreo = new JLabel("Gmail");
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(lblCorreo,gbc);
		//Valida que solo sean correos de gmail
		tfCorreo = new validarCampo("[.0-9a-zA-z]{2,100}+@gmail.com$");
		gbc.gridx = 1;
		gbc.gridy = 5;
		add(tfCorreo,gbc);
		tfCorreo.setToolTipText("Solo se admiten correos de gmail");
		//Agrega la etiqueta para el domicilio
		lblDomicilio = new JLabel("Domicilio");
		gbc.gridx = 0;
		gbc.gridy = 6;
		add(lblDomicilio,gbc);
		
		tfDomicilio = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = 6;
		add(tfDomicilio,gbc);
		//Agrega la etiqueta para la contraseña
		lblPassword = new JLabel("Contraseña");
		gbc.gridx = 0;
		gbc.gridy = 7;
		add(lblPassword,gbc);

		tfPassword = new JTextField();
		gbc.gridx = 2;
		gbc.gridy = 7;
		add(tfPassword,gbc);
		//Agrega ell boton para registrar el agremiado
		btnAceptar = new JButton("Aceptar");
		gbc.gridx = 0;
		gbc.gridy = 8;
		add(btnAceptar,gbc);
		//Agrega el boton para borrar lo que hay en los campos
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
				//Obtiene lo que hay en los campos de l registro
				String[] datos = new String[7];
				datos[0] = tfClave.getText();
				datos[1] = tfNombre.getText();
				datos[2] = tfApellidos.getText();
				datos[3] = tfCel.getText();
				datos[4] = tfCorreo.getText();
				datos[5] = tfDomicilio.getText();
				datos[6] = tfPassword.getText();
				
				//Valida que los campos no esten vacios
				if (datos[0].equals("")||datos[1].equals("")||datos[2].equals("")||datos[3].equals("")||datos[4].equals("")||
				datos[5].equals("")||datos[6].equals("")){
					JOptionPane.showMessageDialog(btnAceptar,"Lllene todos los campos","", 1); 
				}
				//valida el campo del número de celular 
				else if(!tfCel.Esvalido()){
					JOptionPane.showMessageDialog(btnAceptar,"Solo se admiten numeros de 10 digitos.\nEj. 5585746525",
					"Error en el campo Tel. cel.", 1);
					tfCel.grabFocus();
				// valida el correo  
				}else if (!tfCorreo.Esvalido()){
					JOptionPane.showMessageDialog(btnAceptar,"Solo se admiten correos de gmail.\nEj. pedro.torres@gmail.com",
					"Error en el campo Correo", 1);
					tfCorreo.grabFocus();
				//valida que la clave tenga la nomenclatura 
				}else if(!tfClave.Esvalido()){
					JOptionPane.showMessageDialog(btnAceptar,"Solo se admiten número y letras mayusculas de tamaño 26.\nEj. 1306808971ZI30GM135.284733",
					"Error en el campo Clave", 1);
					tfCorreo.grabFocus();
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
	//Borra todos los campos
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
