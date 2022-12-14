package mx.uam.ayd.proyecto.presentacion.principal;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.presentacion.AgregarAgremiado.ControlAgregarAgremiado;
import mx.uam.ayd.proyecto.presentacion.BorrarAgremiado.ControlBorrarAgremiado;
import mx.uam.ayd.proyecto.presentacion.Login.ControlLogin;
import mx.uam.ayd.proyecto.presentacion.Login.VistaLogin;
import mx.uam.ayd.proyecto.presentacion.agendarCita.ControlAgendarCita;
import mx.uam.ayd.proyecto.presentacion.comentarios.ControlEnviarComentario;
import mx.uam.ayd.proyecto.presentacion.consultarAvisos.ControlConsultarAvisos;
import mx.uam.ayd.proyecto.presentacion.consultarCitas.ControlConsultarCitas;
import mx.uam.ayd.proyecto.presentacion.crearPublicacion.ControlCrearPublicacion;
import mx.uam.ayd.proyecto.presentacion.procesarTramites.ControlProcesarTramites;
import mx.uam.ayd.proyecto.presentacion.responderComentario.ControlResponderComentario;
import mx.uam.ayd.proyecto.presentacion.solicitarTramites.ControlSolicitarTramites;

/**
 * Esta clase lleva el flujo de control de la ventana principal
 * 
 * @author humbertocervantes
 *
 */
@Slf4j
@Component
public class ControlPrincipal {

	@Autowired
	private ControlConsultarCitas controlConsultarCitas;
	
	@Autowired
	private ControlAgendarCita controlAgendarCita;

	@Autowired
	private ControlCrearPublicacion controlCrearPublicacion;

	@Autowired
	private ControlConsultarAvisos controlConsultarAvisos;

	@Autowired
	private ControlProcesarTramites controlProcesarTramites;

	@Autowired
	private ControlSolicitarTramites controlSolicitarTramites;

	@Autowired
	private ControlEnviarComentario controlEnviarComentario;

	@Autowired
	private ControlResponderComentario controlResponderComentario;

	@Autowired
	private VentanaPrincipal ventana;
	
	@Autowired
	private VentanaInicio ventanaInicio;

	@Autowired
	VistaLogin vistaLogin;

	private Agremiado agremiado;
	
	private Empleado empleado;
	
	@Autowired
	ControlLogin controlLogin;
	
	@Autowired
	ControlAgregarAgremiado controlAgregarAgremiado;
	
	@Autowired
	ControlBorrarAgremiado controlBorrarAgremiado;
	/**
	 * Inicia el flujo de control de la ventana principal
	 * 
	 */
	public void inicia() {
		vistaLogin.muestra(this);
		ventana.muestra(this);
	}

	public void ventanaInicio() {
		ventanaInicio.muestra(this);
	}

	public void loginAgremiado(Agremiado agremiado) {
		empleado = null;
		this.agremiado = agremiado;
		ventanaInicio.muestra(this);
		ventanaInicio.ActualizaVentanaInicio("agremiado");
	}
	
	public void loginEmpleado(Empleado empleado) {
		agremiado = null;
		this.empleado = empleado;
		ventanaInicio.muestra(this);
		ventanaInicio.ActualizaVentanaInicio(empleado.getTipoEmpleado());
		ventana.setVisibleBtnAgregarAgremiado(true);

		ventana.setVisibleModidficarAviso(true);
		//Valida que sea la encargada para mostrar el boton BorrarAgremiado
		ventana.muestraBotonBorrarAgremiado(empleado.getTipoEmpleado()=="encargada");
	}

	public void cerrarSesion(){
		empleado = null;
		agremiado = null;
		ventana.setVisibleBtnAgregarAgremiado(false);
		ventana.setVisibleModidficarAviso(false);
		ventana.muestraBotonBorrarAgremiado(false);
	} 

	public void agregarAgremiado(){
		controlAgregarAgremiado.inicia();
	}

	public void procesarTramites() {
		controlProcesarTramites.inicia();
	}

  public void tramites() {
		if (agremiado != null)
			controlSolicitarTramites.inicia(agremiado);
		else if (empleado != null)
			controlProcesarTramites.inicia();
	}
	
	public void citas() {
		if (agremiado != null)
			controlAgendarCita.inicia(agremiado);
		else if (empleado != null)
			controlConsultarCitas.inicia();
	}

	public void publicaciones() {
		if (agremiado != null)
			controlConsultarAvisos.inicia(agremiado);

		else if (empleado != null)
			controlCrearPublicacion.inicia(empleado);
	}

	public void ModificarAviso(){
		controlConsultarAvisos.inicia();
	}

	public String encriptada(String contrase??a) throws NoSuchAlgorithmException{
		return controlLogin.encriptar(contrase??a);
	}

	public void comentarios() {
		if (agremiado != null)
			controlEnviarComentario.inicia();
		else if (empleado != null)
			controlResponderComentario.inicia();
	}

	public void borrarAgremiado() {
		// TODO Auto-generated method stub
		controlBorrarAgremiado.borrarAgremiado();
	}

	
}
