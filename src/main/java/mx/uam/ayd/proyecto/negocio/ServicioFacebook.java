package mx.uam.ayd.proyecto.negocio;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Brandon Villada
 *
 */
@Service
public class ServicioFacebook {

	/*
	 * Metodo que nos permite enviar un mensaje sin foto a Facebook
	 * 
	 * @param mensaje Texto que se va a publicar
	 * 
	 * @throws IOException en caso de no se haya podido enviar el mensaje debido a un mal formato de url o por un error en la respuesta de la peticion
	 */
	boolean post(String mensaje) {
		
		//Para el mensaje para la API se necesita que los espacios en blanco se representen como un porcentaje y su numero hexadecimal en ascci
		String mensajeFormateado=mensaje.replace(" ","%20");
		//Prefijo con parte del url de la API y el identificador del grupo
		String cadenaComunPrefijo = "https://graph.facebook.com/v15.0/2431150130380813/feed?message=";
		//Se tiene que renovar el token cada 60 dias si se quiere mantener la funcionalidad
		String cadenaComunSufijo = "&access_token=EAAHmEZAhe2RQBALmImuqMKPIlUaZBGZAQZAzEcmS2CLMtU9SzQodYJkHegcZCPZB8Cu0h8OZC5LZB1xsB9vXhNrVifrZCO4el2m7jjljUyi4hgN156v76ReioT7XHuGSsrzUZClWDQfJXGZCkg33VcmAe8R7JsGz37PWNgE8lg8Qy6yR8NNZCD5uEyi4o06ihZA1tFJgZD";
		try {
			URL url = new URL(cadenaComunPrefijo+mensajeFormateado+cadenaComunSufijo);
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        //Se hace la peticion post a la API para mandar el mensaje
			conn.setRequestMethod("POST");
	        conn.connect();

	        //Se checa si la conexion fue exitosa
	        int codigoRespuesta = conn.getResponseCode();
	        if (codigoRespuesta == 200){
	        	return true;
	        }
	        
		} catch (IOException e) {
			//Hubo un error con el url de la peticion a la API
			return false;
		}
		//Regresa falso por que no se realizo la peticion a la API por un error en el formato de la cadena
		return false;
		
		
	}
}
