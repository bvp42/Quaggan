package mx.uam.ayd.proyecto.presentacion.BorrarAgremiado;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.presentacion.AgregarAgremiado.ControlAgregarAgremiado;
import mx.uam.ayd.proyecto.presentacion.agendarCita.ControlAgendarCita;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;

@Component
public class VistaBorrarAgremiado extends Pantalla{
	private JTextField campoBusqueda;
	private JTable table;
	private ControlBorrarAgremiado controlador;

	public VistaBorrarAgremiado() {
	
	setBounds(new Rectangle(100, 100, 500, 500));
	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[]{40, 56, 0, 278, 0, 40, 0};
	gridBagLayout.rowHeights = new int[]{30, 48, 0, 0, 0, 147, 40, 0};
	gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
	gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
	
	table = new JTable();
	GridBagConstraints gbc_table = new GridBagConstraints();
	gbc_table.insets = new Insets(0, 0, 5, 5);
	gbc_table.fill = GridBagConstraints.BOTH;
	gbc_table.gridx = 3;
	gbc_table.gridy = 5;
	add(table, gbc_table);
	table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"", "Nombre", "Apellido"
			}
		));
	
	JButton borrar = new JButton("Borrar");
	borrar.setEnabled(false);
	borrar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		}
	});
	GridBagConstraints gbc_borrar = new GridBagConstraints();
	gbc_borrar.insets = new Insets(0, 0, 0, 5);
	gbc_borrar.gridx = 3;
	gbc_borrar.gridy = 6;
	add(borrar, gbc_borrar);

	}
	public void muestra(ControlBorrarAgremiado controlador) {
		this.controlador = controlador;
		setVisible(true);
	}

}
