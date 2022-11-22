package mx.uam.ayd.proyecto.negocio;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import mx.uam.ayd.proyecto.datos.AvisoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Aviso;
import mx.uam.ayd.proyecto.negocio.ServicioTelegram;


@Service
public class ServicioAviso {
	@Autowired 
	AvisoRepository avisoRepository;
	Aviso aviso_publicado;
	
	private Calendar obtenerFecha() {
		Calendar fecha = Calendar.getInstance();
		return fecha;
	}

	public boolean crearPublicacion(String imagen, String texto) {
		Calendar fecha = obtenerFecha();
		String cadenaFecha = String.format("%04d-%02d-%02d",fecha.get(Calendar.YEAR),(fecha.get(Calendar.MONTH)+1),fecha.get(Calendar.DAY_OF_MONTH));
		Aviso aviso = new Aviso();
		aviso.setFecha(cadenaFecha);
		aviso.setImagen(imagen);
		if (texto == null) {
			throw new IllegalArgumentException();
		}
		aviso.setContenido(texto);
		
		aviso = avisoRepository.save(aviso);
		
		if (aviso.getIdAviso()>-1) {
			//Vamos a mantener el objeto de tipo aviso para retransmitir la infomacion
			aviso_publicado = aviso;
			return true;
		}else {
			return false;
		}
		
		
	}
	
	public boolean difundirTelegram() throws TelegramApiException {
		String textoMensaje = aviso_publicado.getContenido();
		String chatId = "-1001426324235";
		SendMessage mensaje = new SendMessage();
		mensaje.setChatId(chatId);
		mensaje.setText(textoMensaje);
		ServicioTelegram servicioTelegram = new ServicioTelegram();
		servicioTelegram.sendMsg(chatId, textoMensaje);
		return true;	
		}

	public List<Aviso> recuperaTodos() {
		return avisoRepository.findAll();
	}
	
	
	
}
