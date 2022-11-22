package mx.uam.ayd.proyecto.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.RepositoryAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;

@Service
public class ServicioAgremiado {
    @Autowired
    RepositoryAgremiado repositoryAgremiado;

    public boolean validarAgremiado(String usuario,String password){
        return repositoryAgremiado.existsByClaveAndNombre(password, usuario);
    }

    public Agremiado obtenerAgremiado(String clave){
        return repositoryAgremiado.findByClave(clave);
    }
}
