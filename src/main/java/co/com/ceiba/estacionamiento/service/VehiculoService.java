package co.com.ceiba.estacionamiento.service;

import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.model.VehiculoModel;

public interface VehiculoService {

    public abstract Vehiculo verificarVehiculo(VehiculoModel vehiculo);

}
