package mx.uam.ayd.proyecto.presentacion.BorrarAgremiado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioAgremiado;

@Component
public class ControlBorrarAgremiado {
	@Autowired
	private ServicioAgremiado servicioAgremiado;
	@Autowired
	private VistaBorrarAgremiado vistaBorrarAgremiado; 

	public void borrarAgremiado() {
		vistaBorrarAgremiado.muestra(this);
		
	}

	

}
