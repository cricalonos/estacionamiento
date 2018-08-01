package co.com.ceiba.estacionamiento.restcontroller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.service.IngresoVehiculoService;
import co.com.ceiba.estacionamiento.service.SalidaVehiculoService;

/**
 * @author cristian.londono
 */
@RestController
public class EstacionamientoController {

    private static final Log LOGGER = LogFactory.getLog(EstacionamientoController.class);

    @Autowired
    @Qualifier("ingresoVehiculoService")
    IngresoVehiculoService ingresoVehiculoService;

    @Autowired
    @Qualifier("salidaVehiculoService")
    SalidaVehiculoService salidaVehiculoService;

    @PostMapping("/registrarIngreso")
    public RespuestaDTO registrarIngresoAlEstacionamiento(@RequestBody VehiculoModel vehiculoModel) {
        try {
            return ingresoVehiculoService.registrarIngresoAlEstacionamiento(vehiculoModel);
        } catch (EstacionamientoException e) {
            LOGGER.error(e);
            return new RespuestaDTO(e.getCodigo(), e.getMensaje());
        }
    }

    @GetMapping("/registrarSalida/{placa}")
    public RespuestaDTO registrarSalidaDelEstacionamiento(@PathVariable String placa) {
        try {
            return salidaVehiculoService.registrarSalidaVehiculo(placa);
        } catch (EstacionamientoException e) {
            LOGGER.error(e);
            return new RespuestaDTO(e.getCodigo(), e.getMensaje());
        }
    }
}
