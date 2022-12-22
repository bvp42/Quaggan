package mx.uam.ayd.proyecto.presentacion.consultarAvisos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioAviso;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Aviso;
import mx.uam.ayd.proyecto.negocio.modelo.Cita;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;


/**
 * @author Brandon Villada
 *
 */
@Component
public class ControlConsultarAvisos {
	@Autowired
	private ServicioAviso servicioAviso;
	@Autowired
	private VentanaConsultarAvisos ventanaConsultarAvisos; 

	public List<Aviso> damePublicaciones() {
		return servicioAviso.recuperaTodos();
	}

	public void inicia(Agremiado agremiado) {
			
		List<Aviso> avisos = damePublicaciones();
		ventanaConsultarAvisos.muestra(avisos,false);
		ventanaConsultarAvisos.muestra(this);
	}
	
	public void inicia(Empleado empleado) {
		List<Aviso> avisos = damePublicaciones();
		ventanaConsultarAvisos.muestra(avisos,true);
		ventanaConsultarAvisos.muestra(this);
	}

	public void inicia() {
		List<Aviso> avisos = damePublicaciones();
		ventanaConsultarAvisos.muestra(avisos,true);
		ventanaConsultarAvisos.muestra(this);
	}
	
	public void eliminaAviso(int idAviso){
		servicioAviso.eliminaAviso(idAviso);			
	}

	public void modificaAviso(int idAviso, String nuevoAviso){
		servicioAviso.modificar(idAviso,nuevoAviso);
	}

}
