package mx.uam.ayd.proyecto.util;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class validarCampo extends JTextField{
    
    private Pattern pattern;
    private Border wrongBorder = BorderFactory.createLineBorder(Color.RED);
    private Border defaultBorder;
    private boolean esvalido;

 /**
  * Constructor.
  * @param regEx Expresión regular para evaluar
  */
 public validarCampo(String regEx) {
    super();
    this.defaultBorder = this.getBorder();
    this.setColumns(15);
    this.pattern = Pattern.compile(regEx);
    this.addKeyListener(new KeyListener() {

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        validateText();
    }

    @Override
    public void keyPressed(KeyEvent e) { }
    });
    }
     /**
    * Valida el campo
    *Se pone rojo el borde del campo si no coincide con la expresión regular 
    * @param regEx Expresión regular para evaluar
    * @param esvalido Cambia a true si es valida regEx
    *        false si es invalida regEx   
    */
    private void validateText() {
        Matcher matcher = pattern.matcher(this.getText());
        if (!matcher.matches() ) {
            this.setBorder(wrongBorder);
            this.esvalido = false;
        }else{
            this.setBorder(defaultBorder);
            this.esvalido = true;
        }
    }

    public boolean Esvalido(){
        return this.esvalido;
    } 
}
