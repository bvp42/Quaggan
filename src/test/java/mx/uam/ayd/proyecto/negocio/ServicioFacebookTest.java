package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServicioFacebookTest {
	@InjectMocks
	private ServicioFacebook servicio;
	
	
	@Test
	void postTest() {
		//Prueba 1 cuando se escribe un hipervinculo no valido en el metodo lanza una exception
		assertThrows(IOException.class,() -> new URL(""),"Debio lanzar una exception");
		
		
		//Prueba 2 cuando no se puede crear una coneccion con la api manda una exception
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) new URL("https://").openConnection();
			conn.setRequestMethod("POST");
			assertThrows(IOException.class,() -> conn.connect(),"Debio lanzar una exception");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Se hace la peticion post a la API para mandar el mensaje
		
        
		
		
	}

}
