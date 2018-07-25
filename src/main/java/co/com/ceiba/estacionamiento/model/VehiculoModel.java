package co.com.ceiba.estacionamiento.model;

import java.util.Date;

public class VehiculoModel {

	private Long idVehiculo;
	private String placa;
	private String tipo;
	private Date fechaIngreso;
	private Date fechaSalida;

	public VehiculoModel() {
	}

	public VehiculoModel(Long idVehiculo, String placa, String tipo, Date fechaIngreso, Date fechaSalida) {
		this.idVehiculo = idVehiculo;
		this.placa = placa;
		this.tipo = tipo;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

}
