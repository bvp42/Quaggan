package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.ayd.proyecto.datos.RepositoryAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;

@ExtendWith(MockitoExtension.class)
public class ServicioAgremiadoTest{

    @Mock
    RepositoryAgremiado repositoryAgremiado;

    @InjectMocks
    ServicioAgremiado servicioAgremiado;

    Agremiado agremiado1 = new Agremiado();
    Agremiado agremiado2 = new Agremiado(); 
    boolean datosCorrectos;

    @Test
    void validarAgremiado(){
        agremiado1.setPassword("cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
        agremiado1.setNombre("Alan");

        when(repositoryAgremiado.existsByPasswordAndNombre(agremiado1.getPassword(), agremiado1.getNombre())).thenReturn(true);
        //Prueba 1: Valida datos con clave y nombre correctos
        datosCorrectos = servicioAgremiado.validarAgremiado("Alan", "cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
        assertTrue(datosCorrectos);

        //Prueba 2: Clave incorrecta
        datosCorrectos = servicioAgremiado.validarAgremiado("Alan", "cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a");
        assertFalse(datosCorrectos);

        // Prueba 3: Nombre incorrecto
        datosCorrectos = servicioAgremiado.validarAgremiado("Yo", "cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
        assertFalse(datosCorrectos);

        //Prueba 4: Clave incorrecta y nobre incorrectos
        datosCorrectos = servicioAgremiado.validarAgremiado("Yo", "db4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
        assertFalse(datosCorrectos);
    }

    @Test
    void obtenerAgremiado(){
        agremiado1.setPassword("cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
        agremiado1.setNombre("Alan");

        when(repositoryAgremiado.findByPasswordAndNombre(agremiado1.getPassword(),agremiado1.getNombre())).thenReturn(agremiado1);
        //Prueba 1: Clave correcta
        agremiado2 = servicioAgremiado.obtenerAgremiado("cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8","Alan");
        assertEquals(agremiado1, agremiado2);
        //Prueba 2: Clave incorrecta
        agremiado2 = servicioAgremiado.obtenerAgremiado("db4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8", "Alan");
        assertNull(agremiado2);
    }
}
