package mx.uam.ayd.proyecto.presentacion.AgregarAgremiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.ServicioAgremiado;
import mx.uam.ayd.proyecto.negocio.modelo.Agremiado;



@Component
public class ControlAgregarAgremiado {
    @Autowired
    VistaAgregarAgremiado vistaAgregarAgremiado;
    @Autowired
    ServicioAgremiado servicioAgremiado;
    
    public void inicia(){
        vistaAgregarAgremiado.muestra(this);
    }

    public void AgregarAgremiado(String[] datos){
        Agremiado agremiado = new Agremiado();
        agremiado.setClave(datos[0]);
        agremiado.setNombre((datos[1]));
        agremiado.setApellidos((datos[2]));
        agremiado.setCelular((datos[3]));
        agremiado.setCorreo((datos[4]));
        agremiado.setDomicilio((datos[5]));
        agremiado.setPassword((datos[6]));
        servicioAgremiado.registrarAgremiado(agremiado);
    } 
}
