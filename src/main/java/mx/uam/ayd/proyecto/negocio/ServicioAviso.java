package mx.uam.ayd.proyecto.negocio;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import mx.uam.ayd.proyecto.datos.AvisoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Aviso;

@Service
public class ServicioAviso {
	@Autowired
	AvisoRepository avisoRepository;
	Aviso aviso_publicado;
	Aviso aviso;
	@Autowired
	private ServicioFacebook servicioFacebook;
	
	/*
	 * Evento para borrar un aviso. Es una funcinalidad para el administrador 
	 * 
	 * @autor Isaías Bonilla 
	 * @param idAviso para eliminar el aviso con el id
	 * 
	 */
	public void eliminaAviso(int idAviso){
		aviso = new Aviso(); //Instancia un aviso
		aviso = avisoRepository.findByIdAviso(idAviso);//Crea el aviso con el id 
		avisoRepository.delete(aviso);//Elimina el aviso
	}

	/*
	 * Evento para borrar un aviso. Es una funcinalidad para el administrador 
	 * 
	 * @autor Isaías Bonilla 
	 * @param idAviso para actualizar el aviso
	 * 
	 */
	public void modificar(int idAviso, String nuevocontenido){
		aviso = new Aviso(); //Instancia un aviso
		aviso = avisoRepository.findByIdAviso(idAviso);//Crea el aviso con el id
		aviso.setContenido(nuevocontenido); 
		avisoRepository.save(aviso);//Actualiza el aviso
	}

	private Calendar obtenerFecha() {
		Calendar fecha = Calendar.getInstance();
		return fecha;
	}

	public boolean crearPublicacion(String imagen, String texto) {
		Calendar fecha = obtenerFecha();
		String cadenaFecha = String.format("%04d-%02d-%02d", fecha.get(Calendar.YEAR), (fecha.get(Calendar.MONTH) + 1),
				fecha.get(Calendar.DAY_OF_MONTH));
		Aviso aviso = new Aviso();
		aviso.setFecha(cadenaFecha);
		aviso.setImagen(imagen);
		if (texto == null) {
			throw new IllegalArgumentException();
		}
		aviso.setContenido(texto);

		aviso = avisoRepository.save(aviso);

		if (aviso.getIdAviso() > -1) {
			// Vamos a mantener el objeto de tipo aviso para retransmitir la infomacion
			aviso_publicado = aviso;
			return true;
		} else {
			return false;
		}

	}

	/*
	 * Metodo que permite difundir un aviso a un grupo de Telegram
	 * 
	 * @autor Brandon Villada
	 * 
	 */
	public boolean difundirTelegram() throws TelegramApiException, IOException {
		/*
		 * Si entramos al condicional y es verdadero necesariamente algo paso mal al
		 * almacenar nuestra publicacion por lo que no deberiamos poder segir con la
		 * difusion Por lo que se lanzara una excepcion.
		 *
		 */
		if (aviso_publicado.getIdAviso() <= -1) {
			throw new NullPointerException("No se almaceno la publicacion anterior intentar de nuevo");
		}
		/*
		 * Almacenamos los datos de la publicacion para la difusion
		 */
		
		String textoMensaje = aviso_publicado.getContenido();
		// Id del grupo de Telegram en donde se retransmiten los avisos
		String chatId = "-1001426324235";
		
		String imagen = aviso_publicado.getImagen();
		
		// Creamos una instancia del servicio para poder manejar el bot
		ServicioTelegram servicioTelegram = new ServicioTelegram();

		if (imagen != null) {
			servicioTelegram.sendPhoto(chatId, imagen, textoMensaje);
			return true;
		} else {
			servicioTelegram.sendMessage(chatId, textoMensaje);
			return true;
		}
	}

	public List<Aviso> recuperaTodos() {
		return avisoRepository.findAll();
	}

	/*
	 * Metodo que permite difundir un aviso a un grupo de Facebook
	 * 
	 * @autor Brandon Villada
	 * 
	 */
	public boolean difundirFacebook() {
		/*
		 * Si entramos al condicional y es verdadero necesariamente algo paso mal al
		 * almacenar nuestra publicacion por lo que no deberiamos poder segir con la
		 * difusion Por lo que se lanzara una excepcion.
		 *
		 */
		if (aviso_publicado.getIdAviso() <= -1) {
			throw new NullPointerException("No se almaceno la publicacion anterior intentar de nuevo");
		}
		/*
		 * Almacenamos los datos de la publicacion para la difusion
		 */
		
		String textoMensaje = aviso_publicado.getContenido();
				
		//Regresa verdadero si la peticion fue correcta o falso de lo contrario
		return servicioFacebook.post(textoMensaje);
		
	}

}
