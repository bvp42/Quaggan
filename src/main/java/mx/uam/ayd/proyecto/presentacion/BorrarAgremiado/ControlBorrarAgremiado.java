package mx.uam.ayd.proyecto.presentacion.BorrarAgremiado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;

/**
 * 
 * Control de la vista de Borrar Agremiado
 * 
 * @author Brandon Villada
 *
 */
@Component
public class ControlBorrarAgremiado {
	@Autowired
	private ServicioAgremiado servicioAgremiado;
	@Autowired
	private VistaBorrarAgremiado vistaBorrarAgremiado;
	
	
	public void borrarAgremiado() {
		vistaBorrarAgremiado.muestra(this);

	}

	/*
	 * buscaNombre:Obtiene una lista de agremiados que tienen como nombre el
	 * parametro nombre del servicio
	 * 
	 * @param cadena que identifica el nombre del agremiado
	 * 
	 */
	public void buscaNombre(String nombre, int i) {
		
		List<Agremiado> agremiados = servicioAgremiado.obtenerAgremiado(nombre,i);
		if (agremiados == null || agremiados.size() == 0) {
			vistaBorrarAgremiado.muestraDialogoError();
		} else {
			vistaBorrarAgremiado.muestraAgremiados(agremiados);
		}

	}
	/*
	 * borrarAgremiado:Obtiene un booleano de mandar a llamar al servicio si se elimino el agremiado correctamente
	 */
	public void borrarAgremiado(String agremiado) {
		Boolean respuesta = servicioAgremiado.borraAgremiado(agremiado);
		if (respuesta == true) {
			vistaBorrarAgremiado.muestraDialogoExito();
		}else {
			vistaBorrarAgremiado.muestraDialogoErrorBorrado();
		}
		
	}

}
