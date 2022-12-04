package mx.uam.ayd.proyecto.negocio;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uam.ayd.proyecto.datos.RepositoryAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
/**
 * Servicio para la entidad Agremiado
 * 
 * @author Adolfo Mejía
 */

@Service
public class ServicioAgremiado {
    @Autowired
    RepositoryAgremiado repositoryAgremiado;

    public boolean validarAgremiado(String usuario,String password){
        return repositoryAgremiado.existsByPasswordAndNombre(password, usuario);
    }

    public Agremiado obtenerAgremiado(String password,String usuario){
        return repositoryAgremiado.findByPasswordAndNombre(password, usuario);
    }

    public boolean registrarAgremiado(Agremiado agremiado ){
        if (!repositoryAgremiado.existsByPassword(agremiado.getPassword())){
            if(!repositoryAgremiado.existsById(agremiado.getClave())){
                repositoryAgremiado.save(agremiado);
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"La clave del agrmiaodo ya fue regitrado", "Clave invalida", 0);
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Cambie la contraseña", "Contraseña invalida", 0);
            return false;
        }
        
    }
    
    
    /**
     * Verifica si el agremiado con sesión iniciada tiene la posibilidad de
     * solicitar un trámite o si ya hay uno en progreso
     * 
     * @param agremiado El agremiado con sesión iniciada
     * @return true indicando que puede solicitar un trámite por no tener solicites
     *         activas, false indicando que no puede solicitar un trámite que ya hay
     *         uno en progreso
     */
    public boolean getAccesoATramites(Agremiado agremiado) {
        return agremiado.isAccesoATramites();
    }
}
