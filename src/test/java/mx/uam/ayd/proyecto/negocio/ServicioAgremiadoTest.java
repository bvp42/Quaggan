package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import mx.uam.ayd.proyecto.datos.RepositoryAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;

@ExtendWith(MockitoExtension.class)
public class ServicioAgremiadoTest {


	@Mock
	RepositoryAgremiado repositoryAgremiado;

	@InjectMocks
	ServicioAgremiado servicioAgremiado;

	Agremiado agremiado1 = new Agremiado();
	Agremiado agremiado2 = new Agremiado();
	boolean datosCorrectos;

	@Test
	void validarAgremiado() {
		agremiado1.setPassword("cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
		agremiado1.setNombre("Alan");

		when(repositoryAgremiado.existsByPasswordAndNombre(agremiado1.getPassword(), agremiado1.getNombre()))
				.thenReturn(true);
		// Prueba 1: Valida datos con clave y nombre correctos
		datosCorrectos = servicioAgremiado.validarAgremiado("Alan",
				"cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
		assertTrue(datosCorrectos);

		// Prueba 2: Clave incorrecta
		datosCorrectos = servicioAgremiado.validarAgremiado("Alan",
				"cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a");
		assertFalse(datosCorrectos);

		// Prueba 3: Nombre incorrecto
		datosCorrectos = servicioAgremiado.validarAgremiado("Yo",
				"cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
		assertFalse(datosCorrectos);

		// Prueba 4: Clave incorrecta y nobre incorrectos
		datosCorrectos = servicioAgremiado.validarAgremiado("Yo",
				"db4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
		assertFalse(datosCorrectos);
	}

	@Test
	void obtenerAgremiado() {
		agremiado1.setPassword("cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8");
		agremiado1.setNombre("Alan");

		when(repositoryAgremiado.findByPasswordAndNombre(agremiado1.getPassword(), agremiado1.getNombre()))
				.thenReturn(agremiado1);
		// Prueba 1: Clave correcta
		agremiado2 = servicioAgremiado
				.obtenerAgremiado("cdb4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8", "Alan");
		assertEquals(agremiado1, agremiado2);
		// Prueba 2: Clave incorrecta
		agremiado2 = servicioAgremiado
				.obtenerAgremiado("db4ee2aea69cc6a83331bbe96dc2caa9a299d21329efb0336fc02a82e1839a8", "Alan");
		assertNull(agremiado2);
	}

	@Test
	void registraAgremiado() {
		agremiado1.setPassword("101ee893b1b15600f268a9259688d9b462db3641880df75863da525eacae8b0f");
		agremiado1.setClave("2211682860EA00NK017.091041");
		boolean seRegistroAgremiado;

		// prueba 1 La contrase??a ya existe registrada
		when(repositoryAgremiado.existsByPassword(agremiado1.getPassword())).thenReturn(true);
		seRegistroAgremiado = servicioAgremiado.registrarAgremiado(agremiado1);
		assertFalse(seRegistroAgremiado);

		// Prueba 2 La clave ya fue registrada
		when(repositoryAgremiado.existsByPassword(agremiado1.getPassword())).thenReturn(false);
		when(repositoryAgremiado.existsByClave(agremiado1.getClave())).thenReturn(true);
		seRegistroAgremiado = servicioAgremiado.registrarAgremiado(agremiado1);
		assertFalse(seRegistroAgremiado);

		// Prueba 3 La clave y contrase??a no existen
		when(repositoryAgremiado.existsByPassword(agremiado1.getPassword())).thenReturn(false);
		when(repositoryAgremiado.existsByClave(agremiado1.getClave())).thenReturn(false);
		seRegistroAgremiado = servicioAgremiado.registrarAgremiado(agremiado1);
		assertTrue(seRegistroAgremiado);

	}

	@Test
	void borraAgremiado() {
		//Prueba 1 Se realiza una eliminacion valida
		String clave = "12345";
		Agremiado objetoAgremiado = new Agremiado();

		when(repositoryAgremiado.findByClave(clave)).thenReturn(objetoAgremiado);
		when(repositoryAgremiado.existsByClave(clave)).thenReturn(false);

		Boolean resultado = servicioAgremiado.borraAgremiado(clave);
		assertTrue(resultado);

		
		
		
		//Prueba 2 Verificamos que cuando en algun caso no encuentre el agremiado a borrar regrese Verdadero que indica que no se borro exitosamente
		when(repositoryAgremiado.findByClave(clave)).thenReturn(null);
		when(repositoryAgremiado.existsByClave(clave)).thenReturn(true);

		Boolean resultado2 = servicioAgremiado.borraAgremiado(clave);
		assertFalse(resultado2);

	}
    @Test
    void testDocumentoAceptado() {
        Agremiado agremiado = new Agremiado();
        /*
         * Caso 1: Argumento inv??lido
         */
        try {
            assertThrows(IllegalArgumentException.class, () -> servicioAgremiado.documentoAceptado(null),
                    "Debi?? lanzar IllegalArgumentException");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        /**
         * Caso 2: Argumentos de entrada v??lidos
         */
        try {
            assertTrue(servicioAgremiado.documentoAceptado(agremiado).isAccesoATramites(), "Deber??a ser true");
            assertNull(servicioAgremiado.documentoAceptado(agremiado).getSolicitudActiva(),
                    "No deber??a haber solicitud activa alguna");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
