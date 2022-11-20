package mx.uam.ayd.proyecto.negocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.RepositoryTipoTramite;
import mx.uam.ayd.proyecto.negocio.modelo.TipoTramite;

/**
 * Servicio para la entidad TipoTramite
 * 
 * @author Adolfo Mejía
 */
@Service
public class ServicioTipoTramite {

    @Autowired
    private RepositoryTipoTramite repositoryTipoTramite;

    public List<TipoTramite> findAll() {
        return (List<TipoTramite>) repositoryTipoTramite.findAll();
    }
    
}
