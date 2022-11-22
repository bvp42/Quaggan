package mx.uam.ayd.proyecto.negocio;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



/**
 * Servicio que se utiliza para comunicarse con el API de telegram
 * @author Brandon Villada
 *
 */
public class ServicioTelegram extends TelegramLongPollingBot{
	
	
	@Override
	public void onUpdateReceived(Update update) {
		
	}
	
	public synchronized void sendMsg(String chatId, String s) {
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
	
	@Override
	public String getBotUsername() {
		
		return "enc_snte_sec11_bot";
	}

	@Override
	public String getBotToken() {
		//Token generado 
		return "5434924745:AAFp5OftKY3w4TmJceBEjJsR-hbVUhwI9_Q";
	}

}
