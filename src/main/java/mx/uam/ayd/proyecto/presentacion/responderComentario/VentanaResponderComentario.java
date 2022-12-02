package mx.uam.ayd.proyecto.presentacion.responderComentario;

import java.util.List;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.modelo.Comentario;
import mx.uam.ayd.proyecto.presentacion.compartido.Pantalla;

@Component
public class VentanaResponderComentario extends Pantalla {

    private List<Comentario> comentarios;
    private JList<String> listaComentarios;
    private JScrollPane barraDespl;
    private JTextArea textComentario, textRespuesta;
    private JLabel labelComentario, labelRespuesta, labelEstado;
    private int index;
    private JButton buttonRevisado, buttonResponder;

    public VentanaResponderComentario() {
        setBounds(new Rectangle(100, 100, 500, 500));
        setLayout(null);

        listaComentarios = new JList<String>();
        barraDespl = new JScrollPane(listaComentarios);
        add(barraDespl);
        barraDespl.setBounds(0, 0, 160, 450);

        labelEstado = new JLabel();
        labelEstado.setText("Comentario:");
        add(labelEstado);
        labelEstado.setBounds(300, 0, 150, 20);

        labelComentario = new JLabel();
        labelComentario.setText("Comentario:");
        add(labelComentario);
        labelComentario.setBounds(170, 0, 100, 20);

        textComentario = new JTextArea();
		add(textComentario);
        textComentario.setBounds(170,20, 500, 150);
        textComentario.setEnabled(false);

        labelRespuesta = new JLabel();
        labelRespuesta.setText("Respuesta:");
        add(labelRespuesta);
        labelRespuesta.setBounds(170, 170, 100, 20);

        textRespuesta = new JTextArea();
        add(textRespuesta);
        textRespuesta.setBounds(170, 190, 500, 150);

        
        listaComentarios.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                index = listaComentarios.getSelectedIndex();
                //Comentario comentarioSeleccionado;
                textComentario.setText(comentarios.get(index).getContenido());
                textRespuesta.setText(comentarios.get(index).getRespuesta());
                labelEstado.setText("Estado: " + comentarios.get(index).getEstado());
            }
        });

        buttonRevisado = new JButton("Marcar como revisado");
		buttonRevisado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                if(comentarios.get(index).getEstado().equals("Contestado")){
                    JOptionPane.showMessageDialog(null, "Ya tiene una respuesta");
                }else{
                    comentarios.get(index).setEstado("Revisado");
                    labelEstado.setText("Estado: " + comentarios.get(index).getEstado());
                    JOptionPane.showMessageDialog(null, "Revisado correctamente");
                }
			}
		});
		add(buttonRevisado);
        buttonRevisado.setBounds(200, 360, 200, 30);

        buttonResponder = new JButton("Actualizar Respuesta");
		buttonResponder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                //poner contador de palabras
                StringTokenizer st = new StringTokenizer(textRespuesta.getText());
                if(st.countTokens()<1){
                    JOptionPane.showMessageDialog(null, "Escribe por lo menos 1 palabra");
                }else if(st.countTokens()>200){
                    JOptionPane.showMessageDialog(null, "Escribe maximo 200 palabras");
                }else{
                    comentarios.get(index).setRespuesta(textRespuesta.getText());
                    comentarios.get(index).setEstado("Contestado");
                    labelEstado.setText("Estado: " + comentarios.get(index).getEstado());
                }
			}
		});
		add(buttonResponder);
        buttonResponder.setBounds(410, 360, 200, 30);

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
