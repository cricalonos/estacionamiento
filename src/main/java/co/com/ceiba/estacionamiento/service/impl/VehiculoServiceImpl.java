package co.com.ceiba.estacionamiento.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.converter.VehiculoConverter;
import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.repository.VehiculoRepository;
import co.com.ceiba.estacionamiento.service.VehiculoService;

@Service("vehiculoServiceImpl")
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	@Qualifier("vehiculoRepository")
	private VehiculoRepository vehiculoRepository;

	@Autowired
	@Qualifier("vehiculoConverter")
	private VehiculoConverter vehiculoConverter;

	public VehiculoModel agregarVehiculo(VehiculoModel vehiculoModel) {
		Vehiculo vehiculo = vehiculoRepository.save(vehiculoConverter.convertirModeloAEntidad(vehiculoModel));
		return vehiculoConverter.convertirEntidadAModelo(vehiculo);
	}

}
