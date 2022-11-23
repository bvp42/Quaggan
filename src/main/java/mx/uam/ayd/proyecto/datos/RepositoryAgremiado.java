package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryAgremiado extends CrudRepository<Agremiado, String> {

    public boolean existsByClaveAndNombre(String clave,String Nombre);
    public Agremiado findByClave(String clave);
}
