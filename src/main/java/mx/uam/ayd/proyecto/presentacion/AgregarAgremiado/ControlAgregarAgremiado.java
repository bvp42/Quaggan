package mx.uam.ayd.proyecto.presentacion.AgregarAgremiado;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.uam.ayd.proyecto.negocio.ServicioAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.presentacion.principal.ControlPrincipal;



@Component
public class ControlAgregarAgremiado {
    
    @Autowired
    VistaAgregarAgremiado vistaAgregarAgremiado;
    @Autowired
    ServicioAgremiado servicioAgremiado;
    ControlPrincipal controlPrincipal;
    
    public void inicia(){
        vistaAgregarAgremiado.muestra(this);
    }
    /**
    * @return boolean Indica si el registro fue exitoso
    * @param datos Es un array de String que contiene los datos obtenidos en la vista 
    * de agregar agremiado
    * Crea un objeto Agremiado y le asigna los valores del parametro datos       
    */
    public boolean AgregarAgremiado(String[] datos) throws NoSuchAlgorithmException{
        Agremiado agremiado = new Agremiado();
        agremiado.setClave(datos[0]);
        agremiado.setNombre((datos[1]));
        agremiado.setApellidos((datos[2]));
        agremiado.setCelular((datos[3]));
        agremiado.setCorreo((datos[4]));
        agremiado.setDomicilio((datos[5]));
        agremiado.setPassword(encriptar(datos[6]));
        return servicioAgremiado.registrarAgremiado(agremiado);
    }
    /**
    * @return sha256 Cadena de 64 caracteres en hexadecimal(encriptación)
    * @param contraseña  
    * Encripta una cadena con el algoritmo SHA-256
    * Crea un objeto Agremiado y le asigna los valores del parametro datos       
    */
    public String encriptar(String contraseña) throws NoSuchAlgorithmException{
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(contraseña.getBytes(StandardCharsets.UTF_8));
        contraseña = "";
        String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return sha256;
    }
}
