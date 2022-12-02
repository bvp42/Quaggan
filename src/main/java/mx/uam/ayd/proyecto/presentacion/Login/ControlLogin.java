package mx.uam.ayd.proyecto.presentacion.Login;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioAgremiado;
import mx.uam.ayd.proyecto.negocio.ServicioEmpleado;
import mx.uam.ayd.proyecto.presentacion.principal.ControlPrincipal;


@Component
public class ControlLogin {
    @Autowired
    ServicioEmpleado servicioEmpleado;
    @Autowired
    ServicioAgremiado servicioAgremiado;

    public void validaDatos(ControlPrincipal controlPrincipal,String usuario,String password,int rol) throws NoSuchAlgorithmException{
        boolean datosValidos;
        password = encriptar(password);
        if(rol == 2){// Si el usuario es "empleado"
            datosValidos = servicioEmpleado.validarEmpleado(usuario, password);
            if(datosValidos){
                controlPrincipal.loginEmpleado(servicioEmpleado.obtenEmpleado(usuario, password));//Carga la vista del empleado
            }else{
                JOptionPane.showMessageDialog(null,"Datos incorrectos","Contraseña o usuario incorrenctos", 
                JOptionPane.ERROR_MESSAGE);
            } 
        }else if(rol == 1){//Si el usuario es "agremiado"
            datosValidos = servicioAgremiado.validarAgremiado(usuario, password);//Valida el usuario y la clave del agremiado 
            System.out.println(datosValidos);
            if (datosValidos){
                controlPrincipal.loginAgremiado(servicioAgremiado.obtenerAgremiado(password,usuario));//Carga la vista del agremiado
            }else{
                JOptionPane.showMessageDialog(null,"Datos incorrectos","Contraseña o usuario incorrenctos", 
                JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    /**Encripta con el algoritmo SHA-256
     * 
     * @param contraseña
     * @return 
     * @throws NoSuchAlgorithmException
     * Retorna la contraseña encriptada
     */
    public String encriptar(String contraseña) throws NoSuchAlgorithmException{
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(contraseña.getBytes(StandardCharsets.UTF_8));
        contraseña = "";
        String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return sha256;
    }
}
