package co.com.ceiba.estacionamiento.converter;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.model.VehiculoModel;

@Component("vehiculoConverter")
public class VehiculoConverter {

	public Vehiculo convertirModeloAEntidad(VehiculoModel vehiculoModel) {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setIdVehiculo(vehiculoModel.getIdVehiculo());
		vehiculo.setPlaca(vehiculoModel.getPlaca());
		vehiculo.setFechaIngreso(vehiculoModel.getFechaIngreso());
		vehiculo.setFechaSalida(vehiculoModel.getFechaSalida());
		vehiculo.setTipo(vehiculoModel.getTipo());
		return vehiculo;
	}

	public VehiculoModel convertirEntidadAModelo(Vehiculo vehiculoEntidad) {
		VehiculoModel vehiculo = new VehiculoModel();
		vehiculo.setIdVehiculo(vehiculoEntidad.getIdVehiculo());
		vehiculo.setPlaca(vehiculoEntidad.getPlaca());
		vehiculo.setFechaIngreso(vehiculoEntidad.getFechaIngreso());
		vehiculo.setFechaSalida(vehiculoEntidad.getFechaSalida());
		vehiculo.setTipo(vehiculoEntidad.getTipo());
		return vehiculo;
	}

}
