package mx.uam.ayd.proyecto.negocio.modelo;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Agremiado {

	@ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "agremiado")
    private final List<Cita> citas = new ArrayList<>();

    @Id
    private String clave;
    
    private String nombre;
    
    private String password;

    private String apellidos;

    private String filiacion;

    private String adscripcion;

    private String puesto;

    private String domicilio;

    private String turno;

    private String celular;

    private String telefono;

    private String correo;

    private boolean accesoATramites;

    @OneToOne(targetEntity = SolicitudTramite.class, fetch = FetchType.EAGER)
    private SolicitudTramite solicitudActiva;

    public List<Cita> getCitas(){
        return new ArrayList<>(this.citas);
    }

    public void addCita(Cita cita){
        this.citas.add(cita);
    }
    
    public String getNombreCompleto() {
    	return nombre + " " + apellidos;
    }

    public void nuevaSolicitudRealizada(SolicitudTramite nuevaSolicitud) {
        this.accesoATramites = false;
        this.solicitudActiva = nuevaSolicitud;
    }
}
