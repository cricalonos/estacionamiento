package co.com.ceiba.estacionamiento.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.service.VehiculoService;

/**
 * @author cristian.londono
 */
@RestController
public class EstacionamientoController {

	@Autowired
	@Qualifier("vehiculoServiceImpl")
	VehiculoService vehiculoService;

	@PostMapping("/registrarIngreso")
	public VehiculoModel registrarIngreso(@RequestBody VehiculoModel vehiculoModel) {
		vehiculoService.agregarVehiculo(vehiculoModel);
		return vehiculoModel;
	}
}
