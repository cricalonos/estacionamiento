package co.com.ceiba.estacionamiento.service;

import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;

public interface SalidaVehiculoService {

    public RespuestaDTO registrarSalidaVehiculo(String placa) throws EstacionamientoException;

}
