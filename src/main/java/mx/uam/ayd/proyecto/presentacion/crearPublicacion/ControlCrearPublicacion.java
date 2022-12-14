package mx.uam.ayd.proyecto.presentacion.crearPublicacion;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;


import mx.uam.ayd.proyecto.negocio.ServicioAviso;

import mx.uam.ayd.proyecto.negocio.modelo.Empleado;





@Component
public class ControlCrearPublicacion {
	@Autowired
	private ServicioAviso servicioAviso;
	@Autowired
	private VentanaCrearPublicacion ventanaCrearPublicacion;
	
	 public boolean esEncargada(Empleado empleado) {
		if (empleado.getTipoEmpleado() == "encargada"){
				return true;
		}
		return false;
	}
	
	public void inicia(Empleado empleado) {
		
		if (esEncargada(empleado)) {
			ventanaCrearPublicacion.muestra(this);
		}
	}

	public void crearPublicacion(String imagen, String texto) {
		if (servicioAviso.crearPublicacion(imagen,texto)) {
			ventanaCrearPublicacion.activaLogoConfirmacionOcultaCrear();
		}
		
	}
	/*
	 * Metodo para difundir a Telegram
	 * @autor Brandon Villada 
	 */
	public void difundirTelegram() throws TelegramApiException, IOException {
		servicioAviso.difundirTelegram();
	}
	/*
	 * Metodo para difundir a Facebook
	 * @autor Brandon Villada 
	 */
	public boolean difundirFacebook()  {
		return servicioAviso.difundirFacebook();
	}
	
}
