package mx.uam.ayd.proyecto.negocio;

import java.io.File;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Servicio que se utiliza para comunicarse con el API de telegram
 * 
 * @author Brandon Villada
 *
 */
public class ServicioTelegram extends TelegramLongPollingBot {

	/*
	 * Metodo que se tiene que implementar por defecto
	 * 
	 * @param Update Objeto que mantiene activa la conexion con el bot para
	 * peticiones directas al bot
	 */
	// No se implementara nada en este metodo ya que no requerimos esa funcionalidad
	@Override
	public void onUpdateReceived(Update update) {

	}

	/*
	 * Metodo que nos permite enviar un mensaje sin foto
	 * 
	 * @param chatId identificador del grupo de telegram al cual se envia el mensaje
	 * 
	 * @param s Texto que se va a publicar
	 * 
	 * @throws TelegramApiException en caso de no se haya podido enviar el mensaje
	 */
	public synchronized void sendMessage(String chatId, String s) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId);
		sendMessage.setText(s);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Metodo que nos permite enviar un mensaje con foto
	 * 
	 * @param chatId Identificador del grupo de telegram al cual se envia el mensaje
	 * 
	 * @param rutaPhoto Ruta(local) en la que se encuentra alojada nuestra imagen en
	 * la computadora
	 * 
	 * @param s Texto que se va a publicar
	 * 
	 * @throws TelegramApiException en caso de no se haya podido enviar el mensaje
	 */
	public synchronized void sendPhoto(String chatId, String rutaPhoto, String s) {
		SendPhoto sendPhoto = new SendPhoto();
		File foto = new File(rutaPhoto);
		InputFile fotoIF = new InputFile(foto, "prueba");
		sendPhoto.setChatId(chatId);
		sendPhoto.setCaption(s);
		sendPhoto.setPhoto(fotoIF);
		try {
			execute(sendPhoto);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Metodo que se tiene que implementar que define el nombre de usuario de
	 * nuestro bot
	 */
	@Override
	public String getBotUsername() {

		return "enc_snte_sec11_bot";
	}

	/*
	 * Metodo que se tiene que implementar que define el token de nuestro bot
	 */
	@Override
	public String getBotToken() {
		// Token generado
		return "5434924745:AAFp5OftKY3w4TmJceBEjJsR-hbVUhwI9_Q";
	}

}
