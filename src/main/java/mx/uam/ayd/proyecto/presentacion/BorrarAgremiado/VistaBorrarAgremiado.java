package mx.uam.ayd.proyecto.presentacion.BorrarAgremiado;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.presentacion.AgregarAgremiado.ControlAgregarAgremiado;
import mx.uam.ayd.proyecto.presentacion.agendarCita.ControlAgendarCita;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;

/**
 * Vista que permite Consultar a un agremiado y borrarlo de la base de datos
 * 
 * @author Brandon Villada
 *
 */
@Component
public class VistaBorrarAgremiado extends Pantalla {
	private static final String[] columnas = { "ID", "Nombre", "Apellido" };
	private JTextField campoBusqueda;
	private JTable table;
	private ControlBorrarAgremiado controlador;
	private JButton botonBorrar;
	private JTable tabla;

	@SuppressWarnings("serial")
	public VistaBorrarAgremiado() {

		setBounds(new Rectangle(100, 100, 500, 500));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 56, 0, 278, 0, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 48, 0, 0, 0, 147, 40, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JButton buscar = new JButton("Buscar");
		buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JLabel lblNewLabel = new JLabel("Borrar Agremiado");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_buscar = new GridBagConstraints();
		gbc_buscar.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_buscar.insets = new Insets(0, 0, 5, 5);
		gbc_buscar.gridx = 1;
		gbc_buscar.gridy = 2;
		add(buscar, gbc_buscar);

		campoBusqueda = new JTextField();
		GridBagConstraints gbc_campoBusqueda = new GridBagConstraints();
		gbc_campoBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_campoBusqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoBusqueda.gridx = 3;
		gbc_campoBusqueda.gridy = 2;
		add(campoBusqueda, gbc_campoBusqueda);
		campoBusqueda.setColumns(10);

		JCheckBox nombre = new JCheckBox("Nombre");
		GridBagConstraints gbc_nombre = new GridBagConstraints();
		gbc_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_nombre.gridx = 1;
		gbc_nombre.gridy = 4;
		add(nombre, gbc_nombre);

		JCheckBox apellido = new JCheckBox("Apellido");
		GridBagConstraints gbc_apellido = new GridBagConstraints();
		gbc_apellido.insets = new Insets(0, 0, 5, 5);
		gbc_apellido.gridx = 4;
		gbc_apellido.gridy = 4;
		add(apellido, gbc_apellido);

		JButton borrar = new JButton("Borrar");
		botonBorrar = borrar;
		borrar.setEnabled(false);
		borrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 5;
		add(scrollPane, gbc_scrollPane);

		table = new JTable();
		tabla = table;
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre", "Apellido" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_borrar = new GridBagConstraints();
		gbc_borrar.insets = new Insets(0, 0, 0, 5);
		gbc_borrar.gridx = 3;
		gbc_borrar.gridy = 6;
		add(borrar, gbc_borrar);

		// Si se selecciona una caja se debe deselecionar la otra
		apellido.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				nombre.setSelected(false);
			}
		});
		nombre.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				apellido.setSelected(false);
			}
		});

		buscar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String cadena = campoBusqueda.getText();
				if (nombre.isSelected() || apellido.isSelected()) {
					if(nombre.isSelected()) {
					controlador.buscaNombre(cadena,0);
					}else {
					controlador.buscaNombre(cadena,1);
					}
					
				}else {
					muestraDialogoErrorParametro();
				}
			}
		});
		
		
		borrar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				DefaultTableModel tablaModelo = (DefaultTableModel) tabla.getModel();
				//Verificamos que el boton este activado y que se haya selecionado a un elemento de la tabla
				
				if(botonBorrar.isEnabled() && tabla.getSelectedRow()>=0) {
					String agremiado = (String) tablaModelo.getValueAt(tabla.getSelectedRow(), 0);
					int input = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar?", null,
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (input == 0) {
						controlador.borrarAgremiado(agremiado);
						tablaModelo.removeRow(tabla.getSelectedRow());
					}
					
					
				}
			}
		});

	}

	
	public void muestra(ControlBorrarAgremiado controlador) {
		this.controlador = controlador;
		setVisible(true);
	}

	/*
	 * muestraAgremiados: despliega en la tabla los agremiados que fueron
	 * encontrados
	 */
	public void muestraAgremiados(List<Agremiado> agremiados) {
		DefaultTableModel tablaModelo = (DefaultTableModel) table.getModel();
		for (Agremiado agremiado : agremiados) {
			String[] parametros = { agremiado.getClave(), agremiado.getNombre(), agremiado.getApellidos() };
			tablaModelo.addRow(parametros);
			
		}
		JOptionPane.showConfirmDialog(null, "Se encontraron " + agremiados.size()+ " coincidencias", "Mensaje",
				JOptionPane.DEFAULT_OPTION);
		botonBorrar.setEnabled(true);
	}

	/*
	 * muestraDialogoError: muestra un mensaje de error al no encontrar agremiados
	 * con ese parametro
	 */
	public void muestraDialogoError() {
		JOptionPane.showConfirmDialog(null, "No se encontro ningun agremiado con ese parametro", "Error",
				JOptionPane.DEFAULT_OPTION);
		borrarInformacion();
		botonBorrar.setEnabled(false);
	}
	public void muestraDialogoErrorParametro() {
		JOptionPane.showConfirmDialog(null, "Debe seleccionar al menos una caracteristica a buscar", "Error",
				JOptionPane.DEFAULT_OPTION);
		borrarInformacion();
		botonBorrar.setEnabled(false);
	}
	private void borrarInformacion() {
		DefaultTableModel tablaModelo = (DefaultTableModel) table.getModel();
		int numeroFilas = tablaModelo.getRowCount();
		for (int i = 0; i < numeroFilas; i++) {
			tablaModelo.removeRow(i);
		}
	}


	public void muestraDialogoExito() {
		// TODO Auto-generated method stub
		JOptionPane.showConfirmDialog(null, "Se ha eliminado satisfactoriamente al agremiado", "Mensaje",
				JOptionPane.DEFAULT_OPTION);
		botonBorrar.setEnabled(true);
	}


	public void muestraDialogoErrorBorrado() {
		JOptionPane.showConfirmDialog(null, "Ha ocurrido un error y no se ha podido eliminar", "Error",
				JOptionPane.DEFAULT_OPTION);
		borrarInformacion();
		botonBorrar.setEnabled(false);
	}
}
