package mx.uam.ayd.proyecto.negocio;

import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uam.ayd.proyecto.datos.RepositoryAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudTramite;

/**
 * Servicio para la entidad Agremiado
 * 
 * @author Adolfo Mejía
 */

@Service
public class ServicioAgremiado {
    @Autowired
    RepositoryAgremiado repositoryAgremiado;

    public boolean validarAgremiado(String usuario, String password) {
        return repositoryAgremiado.existsByPasswordAndNombre(password, usuario);
    }

    public Agremiado obtenerAgremiado(String password, String usuario) {
        return repositoryAgremiado.findByPasswordAndNombre(password, usuario);
    }

    /**
     * Registra a un gremiado verificando que no haya sido registrado y
     * que no se repitan las contraseñas
     * 
     * @param agremiado
     * @return true indica que el agremiado fue registrado exitosamente
     *         false indica que no se pudo registrar el agremiado porque
     *         se repite la clave y/o contraseña
     */
    public boolean registrarAgremiado(Agremiado agremiado) {
        if (!repositoryAgremiado.existsByPassword(agremiado.getPassword())) {
            if (!repositoryAgremiado.existsByClave(agremiado.getClave())) {
                repositoryAgremiado.save(agremiado);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "La clave del agremiado ya fue registrada", "Clave invalida", 0);
                return false;
            }
        } else {
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

    /**
     * Método que regresa la solicitud activa de un agremiado
     * 
     * @param agremiado agremiado con sesión iniciada
     * @return la solicitud de trámite activa
     */
    public SolicitudTramite getSolicitudActiva(Agremiado agremiado) {
        return agremiado.getSolicitudActiva();
    }

    /**
     * @param agremiado Agremiado con sesión iniciada que ha aceptado un documento
     *                  de trámite
     * @return el agremiado con los datos actualizados: reestablecido su acceso a
     *         trámites y desligada la solicitud de trámite activa
     * @throws IllegalArgumentException
     */
    public Agremiado documentoAceptado(Agremiado agremiado) throws IllegalArgumentException {

        if (agremiado == null) {
            throw new IllegalArgumentException("Argumento no válido");
        }
        agremiado.setAccesoATramites(true);
        agremiado.setSolicitudActiva(null);
        repositoryAgremiado.save(agremiado);

        return agremiado;
    }
}
