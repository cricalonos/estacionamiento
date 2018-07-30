package co.com.ceiba.estacionamiento.service;

import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;

public interface EstacionamientoService {

    public abstract RespuestaDTO registrarIngresoAlEstacionamiento(VehiculoModel vehiculoModel)
            throws EstacionamientoException;

}
