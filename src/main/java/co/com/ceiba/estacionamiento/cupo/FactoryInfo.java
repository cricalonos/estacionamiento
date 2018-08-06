package co.com.ceiba.estacionamiento.cupo;

import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

public class FactoryInfo {

    public static InfoVehiculo getInfo(TipoVehiculoEnum tipo) throws EstacionamientoException {
        InfoVehiculo info = null;
        switch (tipo) {
        case MOTO:
            info = new InfoMoto();
            break;
        case CARRO:
            info = new InfoCarro();
            break;
        default:
            throw new EstacionamientoException(CodigoMensajeEnum.ERROR_TIPO_VEHICULO);
        }
        return info;
    }

}
