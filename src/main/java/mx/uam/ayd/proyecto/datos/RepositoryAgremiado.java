package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RepositoryAgremiado extends CrudRepository<Agremiado, String> {

    public boolean existsByPassword(String password);
    public boolean existsByPasswordAndNombre(String password,String nombre);
    public Agremiado findByPasswordAndNombre(String password, String nombre);
    public boolean existsByClave(String clave);
	public List<Agremiado> findByNombre(String nombre);
	public void delete(Agremiado agremiado);
	public Agremiado findByClave(String clave);
	public List<Agremiado> findByApellidos(String apellido);
}
