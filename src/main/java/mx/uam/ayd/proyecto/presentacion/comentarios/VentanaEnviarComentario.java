package mx.uam.ayd.proyecto.presentacion.comentarios;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import java.util.Date;
import java.util.List;
import javax.swing.JScrollPane;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Clase ventana que sirve para Enviar un Comentario
* @author Ivan Omar
* @since 19/12/2022
*/
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

	/**
	* VentanaEnviarComentario: Tiene el codigo de la ventana son sus layout, botones, eventos, etc.
	* @param Ninguno No tiene parametros de entrada 
	* @return No tiene parametros de salida
	*/
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
				String s = textArea.getText();
				int contador = 1, pos;
				s = s.trim(); //eliminar los posibles espacios en blanco al principio y al final                              
				if (s.isEmpty()) {
					contador = 0;
				} else {
						pos = s.indexOf(" "); //se busca el primer espacio en blanco
						while (pos != -1) {   //mientras que se encuentre un espacio en blanco
							contador++;    
							pos = s.indexOf(" ", pos + 1); //se busca el siguiente espacio en blanco                       
						}                                     
				} 
				if(contador == 0){
					JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener minimo 1 palabra");
				}else if(contador>200){
					JOptionPane.showMessageDialog(null, "Error, el mensaje debe tener maximo 200 palabras ");
				}else{
					//guardar comentario
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
				}
			}
		});
		add(btnEnviar);
        btnEnviar.setBounds(400, 400, 200, 30);

		textArea = new JTextArea();
		textArea.setVisible(false);
		add(textArea);
		textArea.setBounds(100, 60, 500, 300);
	}
	
	/**
	* muestra: descripción del método
	* @param comentarios lista de comentarios guardados en el repositorio
	* @return No retorna valores
	*/
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