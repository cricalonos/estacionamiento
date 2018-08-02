package co.com.ceiba.estacionamiento.service;

import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;

public interface ConsultaVehiculoService {

    public RespuestaDTO consultarVehiculosEstacionados() throws EstacionamientoException;

}
