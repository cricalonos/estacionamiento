package co.com.ceiba.estacionamiento.restcontroller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.service.ConsultaVehiculoService;
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

    @Autowired
    @Qualifier("consultaVehiculoService")
    ConsultaVehiculoService consultaVehiculoService;

    @PostMapping("/registrarIngreso")
    public ResponseEntity<RespuestaDTO> registrarIngreso(@RequestBody(required = true) VehiculoModel vehiculoModel) {
        try {
            return new ResponseEntity<>(ingresoVehiculoService.registrarIngresoAlEstacionamiento(vehiculoModel),
                    HttpStatus.OK);
        } catch (EstacionamientoException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(new RespuestaDTO(e.getCodigo(), e.getMensaje()), HttpStatus.OK);
        }
    }

    @GetMapping("/registrarSalida/{placa}")
    public ResponseEntity<RespuestaDTO> registrarSalida(@PathVariable(required = true) String placa) {
        try {
            return new ResponseEntity<>(salidaVehiculoService.registrarSalidaVehiculo(placa), HttpStatus.OK);
        } catch (EstacionamientoException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(new RespuestaDTO(e.getCodigo(), e.getMensaje()), HttpStatus.OK);
        }
    }

    @GetMapping("/consultarVehiculos")
    public ResponseEntity<RespuestaDTO> consultarVehiculos() {
        try {
            return new ResponseEntity<>(consultaVehiculoService.consultarVehiculosEstacionados(), HttpStatus.OK);
        } catch (EstacionamientoException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(new RespuestaDTO(e.getCodigo(), e.getMensaje()), HttpStatus.OK);
        }
    }
}
