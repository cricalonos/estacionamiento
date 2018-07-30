package co.com.ceiba.estacionamiento.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.service.EstacionamientoService;

/**
 * @author cristian.londono
 */
@RestController
public class EstacionamientoController {

    @Autowired
    @Qualifier("vehiculoServiceImpl")
    EstacionamientoService vehiculoService;

    @PostMapping("/registrarIngreso")
    public RespuestaDTO registrarIngresoAlEstacionamiento(@RequestBody VehiculoModel vehiculoModel) {
        try {
            return vehiculoService.registrarIngresoAlEstacionamiento(vehiculoModel);
        } catch (EstacionamientoException e) {
            return new RespuestaDTO(e.getCodigo(), e.getMensaje());
        }
    }
}
