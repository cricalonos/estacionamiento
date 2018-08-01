package co.com.ceiba.estacionamiento.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class RegistroEstacionamiento {

    @Id
    @GeneratedValue
    private Long idEstacionamiento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;

    private BigDecimal costo;

    @ManyToOne
    private Vehiculo vehiculo;

    public RegistroEstacionamiento() {
        super();
    }

    public RegistroEstacionamiento(Long idEstacionamiento, Date fechaIngreso, Date fechaSalida, BigDecimal costo,
            Vehiculo vehiculo) {
        super();
        this.idEstacionamiento = idEstacionamiento;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.costo = costo;
        this.vehiculo = vehiculo;
    }

    public Long getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(Long idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

}
