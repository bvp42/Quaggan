package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryAgremiado extends CrudRepository<Agremiado, String> {

    public boolean existsByPassword(String password);
    public boolean existsByPasswordAndNombre(String password,String nombre);
    public Agremiado findByPasswordAndNombre(String password, String nombre);
    public boolean existsByClave(String clave);
}
