package co.com.ceiba.estacionamiento.converter;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.model.VehiculoModel;

@Component("vehiculoConverter")
public class VehiculoConverter {

    public Vehiculo convertirModeloAEntidad(VehiculoModel vehiculoModel) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(vehiculoModel.getPlaca());
        vehiculo.setTipoVehiculo(TipoVehiculoEnum.valueOf(vehiculoModel.getTipo()));
        return vehiculo;
    }
}
