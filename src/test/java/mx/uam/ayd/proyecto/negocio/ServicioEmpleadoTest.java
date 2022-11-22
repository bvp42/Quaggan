package mx.uam.ayd.proyecto.negocio;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import mx.uam.ayd.proyecto.datos.RepositoryEmpleado;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;

@ExtendWith(MockitoExtension.class)
public class ServicioEmpleadoTest{
    
    @Mock
    RepositoryEmpleado repositoryEmpleado;
    
    @InjectMocks
    ServicioEmpleado servicioEmpleado;

    Empleado empleado1 = new Empleado();
    boolean datosCorrectos; 
    Empleado empleado = new Empleado();
    
    @Test
    void validarEmpleadoTest(){
        empleado.setId(1);
        empleado.setNombre("Yanely");
        empleado.setContraseña("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"); 
        empleado.setApellidos("Bermejo Hernandez");
        empleado.setTipoEmpleado("encargada");
		
        
        when(repositoryEmpleado.existsByContraseñaAndNombre(empleado.getContraseña(),empleado.getNombre())).thenReturn(true);
        // Prueba 1: El usuario y contraseña son correctos
        datosCorrectos = servicioEmpleado.validarEmpleado("Yanely", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        assertTrue(datosCorrectos);

        // Prueba 2: El usuario es incorrecto
        datosCorrectos = servicioEmpleado.validarEmpleado("Yanel", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        assertFalse(datosCorrectos);

        //Prueba 3 : La contaseña es incorrecta
        datosCorrectos = servicioEmpleado.validarEmpleado("Yanely", "45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        assertFalse(datosCorrectos);
        
        //Pueba 4: La contraseña y usuarios son incorrectos
        datosCorrectos = servicioEmpleado.validarEmpleado("Yanel", "45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        assertFalse(datosCorrectos);
    }

    @Test
    void obtenEmpleado(){
        empleado.setId(1);
        empleado.setNombre("Yanely");
        empleado.setContraseña("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"); 
        empleado.setApellidos("Bermejo Hernandez");
        empleado.setTipoEmpleado("encargada");
        
        when(repositoryEmpleado.findByContraseñaAndNombre(empleado.getContraseña(),empleado.getNombre())).thenReturn(empleado);
        //Prueba 5: Obtiene al empleado por su contraseña y nombre
        empleado1 = servicioEmpleado.obtenEmpleado("Yanely", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        assertEquals(empleado, empleado1);
        
        //Prueba 6: No obtiene al empleado si la contaseña es incorrecta
        empleado1 = servicioEmpleado.obtenEmpleado("Yanely", "665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        assertNotEquals(empleado, empleado1);

        //Prueba 7: No obtiene al emnpleado si el usuario es incorrecto
        empleado1 = servicioEmpleado.obtenEmpleado("Yanely", "665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3");
        assertNotEquals(empleado, empleado1);
    }
}