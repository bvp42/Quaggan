package mx.uam.ayd.proyecto.presentacion.consultarAvisos;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import java.awt.Color;

import mx.uam.ayd.proyecto.negocio.modelo.Aviso;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import net.bytebuddy.asm.Advice.This;

/**
 * @author Brandon Villada
 *
 */
@Component
public class VentanaConsultarAvisos extends Pantalla{
	private ControlConsultarAvisos controlador;
	private JTabbedPane paneles;
	private JButton btnModificar;
	private JButton btnBorrarAviso;
	private String contenido;
    private String fecha;
	private String imagen;
	private JScrollPane textoAviso;
	private Componente aux;
	int[] ids;
	List<Aviso> avisos;
	Boolean visible;

	public VentanaConsultarAvisos() {
	setBounds(new Rectangle(100, 100, 500, 500));
	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[]{40, 300, 40, 0};
	gridBagLayout.rowHeights = new int[]{30, 48, 147, 40, 0};
	gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
	gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
	setLayout(gridBagLayout);
	
	
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
	paneles = tabbedPane;
	tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
	gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
	gbc_tabbedPane.fill = GridBagConstraints.BOTH;
	gbc_tabbedPane.gridx = 1;
	gbc_tabbedPane.gridy = 2;
	add(tabbedPane, gbc_tabbedPane);
	
	/*
	 * Evento para modificar el aviso actual
	 * Es una funcinalidad para el administrador 
	 * 
	 * @autor Isaías Bonilla 
	 * 
	 */
	btnModificar= new JButton("Modificar Aviso");
	btnModificar.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(ids.length == 0){// Si no hay avisos para modificar 
				JOptionPane.showMessageDialog(paneles,"No hay avisos para modificar","Avisos", 0);
			}else{//En otro caso se da la opcion de modificar el anuncio	
				//Mensaje para confirmar la modificacion del mesaje
				int seleccion = JOptionPane.showOptionDialog( paneles,"¿Seguro que quiere modificar el aviso actual?",
									"Avisos",JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
									new Object[] { "Si", "No"},"opcion 1");//Si es igual a 0 y No es igual 1
				if (seleccion ==0){// Si se seleciono la opcion "Si" modifica el aviso
					int id = paneles.getSelectedIndex();//Dame el indice del aviso seleccionado 
					int idAviso = ids[id];//Asigna el id del aviso selccionado
					Componente componentes = (Componente) paneles.getSelectedComponent();//Obtiene los componentes del panel actual
					JTextArea txt_contenido =  (JTextArea) componentes.getComponent(2);//Obtiene el componente de JtextArea
					contenido = txt_contenido.getText();//Obtiene el texto de 
					controlador.modificaAviso(idAviso,contenido);//Elinima el aviso
					JOptionPane.showMessageDialog(paneles,"El aviso fue modificado correctamente","Avisos",1);//Mensaje de correccion de aviso exitoso 
				}	
			}
		}
	});
		GridBagConstraints gbc_btnModificar = new GridBagConstraints();
		gbc_btnModificar.gridwidth = 1;
		gbc_btnModificar.insets = new Insets(0, 0, 0, 0);
		gbc_btnModificar.gridx = 0;
		gbc_btnModificar.gridy = 5;
		add(btnModificar, gbc_btnModificar);

	/*
	 * Evento para borrar un aviso. Es una funcinalidad para el administrador 
	 * 
	 * @autor Isaías Bonilla 
	 * 
	 */
		btnBorrarAviso= new JButton("Eliminar Aviso");// Crea el boton para eliminar aviso
		btnBorrarAviso.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) { 
			if(ids.length == 0){// Si no hay avisos para eliminar 
				JOptionPane.showMessageDialog(paneles,"No hay avisos para eliminar","Avisos", 0);
			}else{//En otro caso se da la opcion de eliminar el aviso 
				//Mensaje para confirmar la eliminacion del mesaje
				int seleccion = JOptionPane.showOptionDialog( paneles,"¿Seguro que quiere eliminar el aviso actual?",
  								"Avisos",JOptionPane.YES_NO_CANCEL_OPTION,
   								JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
  								new Object[] { "Si", "No"},"opcion 1");// Si es igual a 0 y No es igual 1	
				
				if (seleccion == 0){// Si se seleciono la opcion "Si" elimina el aviso
					int id = paneles.getSelectedIndex();//Dame el indice del aviso seleccionado 
					int idAviso = ids[id];//Asigna el id del aviso selccionado
					controlador.eliminaAviso(idAviso);//Elinima el aviso
					controlador.inicia(); //Actualiza la lista de los avisos
					JOptionPane.showMessageDialog(paneles,"El aviso fue eliminado correctamente","Avisos",1);//Mensaje de eliminacion de aviso exitoso
				}	 
			}
		}
	});
		//Configuracion de la posicion del boton elimimnar aviso
		GridBagConstraints gbc_BorrarAviso = new GridBagConstraints();
		gbc_BorrarAviso.gridwidth = 1;
		gbc_BorrarAviso.insets = new Insets(0, 0, 5, 5);
		gbc_BorrarAviso.gridx = 1;
		gbc_BorrarAviso.gridy = 5;
		add(btnBorrarAviso, gbc_BorrarAviso);
		btnModificar.setVisible(false);
	}

	public void muestra(ControlConsultarAvisos controlador) {
		this.controlador = controlador;
		setVisible(true);
	}	
	
	public void muestra(List<Aviso> avisos, Boolean visible) {
		int id;
		int contador = 0;
		avisos = new ArrayList<Aviso>(Lists.reverse(avisos)); 
		paneles.removeAll();
		btnModificar.setVisible(visible);
		btnBorrarAviso.setVisible(visible);
		ids = new int[avisos.size()];//Instanciar un array del tamaño de la cantidad de avisos que hay
		for (Aviso aviso : avisos) {
			aux = new Componente();
			contenido = (aviso.getContenido());
            fecha =aviso.getFecha();
            imagen=aviso.getImagen();
			if (visible == true){
				id = aviso.getIdAviso();//Obtiene el id del aviso 
				ids[contador] = id;//Inserta en el array el id del aviso para poder eliminarlo o modificarlo
				if (imagen != "null") {
					ImageIcon imagenico = new ImageIcon(imagen);
					aux.setParams(contenido, fecha,imagenico);
				}else {
				aux.setParams(contenido, fecha);
				}
			}else{ 
				if (imagen != "null") {
					ImageIcon imagenico = new ImageIcon(imagen);
					aux.setParams(contenido, fecha,imagenico);
				}else {
				aux.setParams(contenido, fecha);
				}
			}	
            paneles.add(aux);
			aux.setHabilitar(visible);//Se habilita el texto para poder modificarlos si es empleda. Si es emplead el visible = true
			contador = contador + 1;
		}
	}
	
	
	/*public void muestra(List<Aviso> avisos, Boolean visible) {
		int id;
		int contador = 0;
		avisos = new ArrayList<Aviso>(Lists.reverse(avisos)); 
		paneles.removeAll();
		btnModificar.setVisible(visible);
		btnBorrarAviso.setVisible(visible);
		ids = new int[avisos.size()];//Instanciar un array del tamaño de la cantidad de avisos que hay
		for (Aviso aviso : avisos) {
			aux = new Componente();
			contenido = (aviso.getContenido());
            fecha =aviso.getFecha();
            imagen=aviso.getImagen();
			if (visible == true){
				id = aviso.getIdAviso();//Obtiene el id del aviso 
				ids[contador] = id;//Inserta en el array el id del aviso para poder eliminarlo o modificarlo
			} 
			if (imagen != "null") {
            	ImageIcon imagenico = new ImageIcon(imagen);
            	aux.setParams(contenido, fecha,imagenico);
            }else {
            aux.setParams(contenido, fecha);
            }
            paneles.add(aux);
			aux.setHabilitar(visible);//Se habilita el texto para poder modificarlos si es empleda. Si es emplead el visible = true
			contador = contador + 1;
		}
	}*/

}
