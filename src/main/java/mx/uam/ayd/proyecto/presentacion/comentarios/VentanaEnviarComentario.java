package mx.uam.ayd.proyecto.presentacion.comentarios;

import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.modelo.Cita;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import mx.uam.ayd.proyecto.util.Filtro;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Ventana que permite al personal administrativo consultar una lista de citas
 * 
 * @author Antar Espadas
 */
@Slf4j
@Component
public class VentanaEnviarComentario extends Pantalla {
	
	private List<Comentario> comentarios;
    private JList<String> listaComentarios;
    private JScrollPane barraDespl;
	private int index;
	private JTextArea textComentario, textRespuesta, textArea;
	private JLabel labelComentario, labelRespuesta, labelExplicacion1, labelExplicacion2;
	private final JButton btnCancelar;
	private final JButton btnEnviar;
	private final JButton btnHacerComentario;

	public VentanaEnviarComentario() {
		setBounds(new Rectangle(100, 100, 500, 500));
        setLayout(null);

		labelExplicacion1 = new JLabel();
        labelExplicacion1.setText("Comentarios:");
        add(labelExplicacion1);
        labelExplicacion1.setBounds(0, 0, 100, 20);

		labelExplicacion2 = new JLabel();
        labelExplicacion2.setText("Ecribe 1 comentario maximo 200 palabras mimino 1 palabra:");
        add(labelExplicacion2);
        labelExplicacion2.setBounds(180, 30, 400, 20);
		labelExplicacion2.setVisible(false);

		labelComentario = new JLabel();
        labelComentario.setText("Comentario:");
        add(labelComentario);
        labelComentario.setBounds(170, 30, 100, 20);

        textComentario = new JTextArea();
		add(textComentario);
        textComentario.setBounds(170,60, 500, 150);
        textComentario.setEnabled(false);

        labelRespuesta = new JLabel();
        labelRespuesta.setText("Respuesta:");
        add(labelRespuesta);
        labelRespuesta.setBounds(170, 210, 100, 20);

        textRespuesta = new JTextArea();
        add(textRespuesta);
        textRespuesta.setBounds(170, 230, 500, 150);
		textRespuesta.setEnabled(false);

		btnHacerComentario = new JButton("Hacer un comentario");
		btnHacerComentario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnHacerComentario.setVisible(false);
				btnCancelar.setVisible(true);
				btnEnviar.setVisible(true);
				barraDespl.setVisible(false);
				textArea.setVisible(true);
				textRespuesta.setVisible(false);
				labelRespuesta.setVisible(false);
				textComentario.setVisible(false);
				labelComentario.setVisible(false);
				labelExplicacion2.setVisible(true);
				labelExplicacion1.setVisible(false);
			}
		});
		add(btnHacerComentario);
        btnHacerComentario.setBounds(450, 0, 200, 30);

		listaComentarios = new JList<String>();
        barraDespl = new JScrollPane(listaComentarios);
        add(barraDespl);
        barraDespl.setBounds(0, 30, 160, 400);

		listaComentarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                index = listaComentarios.getSelectedIndex();
                //Comentario comentarioSeleccionado;
                textComentario.setText(comentarios.get(index).getContenido());
                textRespuesta.setText(comentarios.get(index).getRespuesta());
                //labelEstado.setText("Estado: " + comentarios.get(index).getEstado());
            }
        });

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setVisible(false);
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnHacerComentario.setVisible(true);
				btnCancelar.setVisible(false);
				btnEnviar.setVisible(false);
				
				textArea.setText("");
				
				barraDespl.setVisible(true);
				textArea.setVisible(false);
				textRespuesta.setVisible(true);
				labelRespuesta.setVisible(true);
				textComentario.setVisible(true);
				labelComentario.setVisible(true);
				labelExplicacion2.setVisible(false);
				labelExplicacion1.setVisible(true);
			}
		});
		add(btnCancelar);
        btnCancelar.setBounds(100, 400, 200, 30);

		btnEnviar = new JButton("Enviar");
		btnEnviar.setVisible(false);
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener maximo 500 palabras ");
				
				String s = textArea.getText();
				//hacer un contador de palabras 
				
				int contador = 1, pos;
				s = s.trim(); //eliminar los posibles espacios en blanco al principio y al final                              
				if (s.isEmpty()) { //si la cadena está vacía
					contador = 0;
				} else {
						pos = s.indexOf(" "); //se busca el primer espacio en blanco
						while (pos != -1) {   //mientras que se encuentre un espacio en blanco
							contador++;    //se cuenta una palabra
							pos = s.indexOf(" ", pos + 1); //se busca el siguiente espacio en blanco                       
						}                                     //a continuación del actual
				} 

				if(contador == 0){
					JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener minimo 1 palabra");
				}else if(contador>200){
					JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener maximo 200 palabras ");
				}else{
					//guardar comentario:
					var Objeto = new Date();
					var fecha = Objeto.toLocaleString();
					JOptionPane.showMessageDialog(null, "fecha: " + fecha + "  palabras: " + contador);
					

					JOptionPane.showMessageDialog(null, "Enviado correctamente ");
					btnHacerComentario.setVisible(true);
					btnCancelar.setVisible(false);
					btnEnviar.setVisible(false);
					
					textArea.setText("");
					
					barraDespl.setVisible(true);
					textArea.setVisible(false);
					textRespuesta.setVisible(true);
					labelRespuesta.setVisible(true);
					textComentario.setVisible(true);
					labelComentario.setVisible(true);
					labelExplicacion2.setVisible(false);
					labelExplicacion1.setVisible(true);
					
					//gridBagLayout.rowHeights[0] = 30;
					//gridBagLayout.rowHeights[1] = 1;
					//gridBagLayout.rowHeights[2] = 1; 
				}
			}
		});
		add(btnEnviar);
        btnEnviar.setBounds(400, 400, 200, 30);

		textArea = new JTextArea();
		textArea.setVisible(false);
		add(textArea);
		textArea.setBounds(100, 60, 500, 300);

		/* 
		 
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVisible(false);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		add(scrollPane_1, gbc_scrollPane_1);
		
		panelFiltros = new JPanel();
		scrollPane_1.setViewportView(panelFiltros);
		panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));
		
		var model = new DefaultComboBoxModel<String>();
		model.addElement("1");
		model.addElement("2");
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setVisible(false);
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnHacerComentario.setVisible(true);
				btnCancelar.setVisible(false);
				btnEnviar.setVisible(false);
				//btnAgregarFiltro.setVisible(false);
				scrollPane_1.setVisible(false);
				textArea.setVisible(false);
				
				
				gridBagLayout.rowHeights[0] = 30;
				gridBagLayout.rowHeights[1] = 1;
				gridBagLayout.rowHeights[2] = 1;
			}
		});
		GridBagConstraints gbc_btnTerminar = new GridBagConstraints();
		gbc_btnTerminar.fill = GridBagConstraints.VERTICAL;
		gbc_btnTerminar.insets = new Insets(0, 0, 0, 5);
		gbc_btnTerminar.gridx = 0;
		gbc_btnTerminar.gridy = 0;
		panel.add(btnCancelar, gbc_btnTerminar);


		btnEnviar = new JButton("Enviar");
		btnEnviar.setVisible(false);
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener maximo 500 palabras ");
				
				String s = textArea.getText();
				//hacer un contador de palabras 
				
				int contador = 1, pos;
				s = s.trim(); //eliminar los posibles espacios en blanco al principio y al final                              
				if (s.isEmpty()) { //si la cadena está vacía
					contador = 0;
				} else {
						pos = s.indexOf(" "); //se busca el primer espacio en blanco
						while (pos != -1) {   //mientras que se encuentre un espacio en blanco
							contador++;    //se cuenta una palabra
							pos = s.indexOf(" ", pos + 1); //se busca el siguiente espacio en blanco                       
						}                                     //a continuación del actual
				} 

				if(contador == 0){
					JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener minimo 1 palabra");
				}else if(contador>200){
					JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener maximo 200 palabras ");
				}else{
					//guardar comentario:
					var Objeto = new Date();
					var fecha = Objeto.toLocaleString();
					JOptionPane.showMessageDialog(null, "fecha: " + fecha + "  palabras: " + contador);
					

					JOptionPane.showMessageDialog(null, "Enviado correctamente ");
					btnHacerComentario.setVisible(true);
					btnCancelar.setVisible(false);
					btnEnviar.setVisible(false);
					scrollPane_1.setVisible(false);
					textArea.setText("");
					textArea.setVisible(false);
					
					gridBagLayout.rowHeights[0] = 30;
					gridBagLayout.rowHeights[1] = 1;
					gridBagLayout.rowHeights[2] = 1; 
				}
			}
		});
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.fill = GridBagConstraints.VERTICAL;
		gbc_btnEnviar.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnviar.gridx = 2;
		gbc_btnEnviar.gridy = 0;
		panel.add(btnEnviar, gbc_btnEnviar);

		
		
		textArea = new JTextArea();
		textArea.setVisible(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 1;
		add(textArea, gbc_textArea);

		btnAgregarFiltro = new JButton("Agregar filtro");
		btnAgregarFiltro.setVisible(false);
		GridBagConstraints gbc_btnAgregarFiltro = new GridBagConstraints();
		gbc_btnAgregarFiltro.gridx = 2;
		gbc_btnAgregarFiltro.gridy = 0;
		panel.add(btnAgregarFiltro, gbc_btnAgregarFiltro);
		btnAgregarFiltro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				agregarFiltro();
			}
		});
		btnAgregarFiltro.addActionListener(e -> {
		});

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Fecha", "Nombre", "Motivo"
			}
		));
		scrollPane.setViewportView(table);
		*/
	}
	
	void muestra(List<Comentario> comentarios) {

        this.comentarios = comentarios;

        String[] datosListaComentarios = new String[comentarios.size()];

        int i = 0;
        for (Comentario comentario : comentarios) {
            datosListaComentarios[i] = comentario.getIdComentario()+ " - " + comentario.getFecha();
            i++;
        }

        listaComentarios.setListData(datosListaComentarios);
        setVisible(true);
    }
	
	
	
}