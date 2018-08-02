package co.com.ceiba.estacionamiento.dto;

import java.util.List;

import co.com.ceiba.estacionamiento.model.RegistroEstacionamientoModel;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

public class ConsultaVehiculoDTO extends RespuestaDTO {

    private List<RegistroEstacionamientoModel> listadoVehiculos;

    public ConsultaVehiculoDTO() {
    }

    public ConsultaVehiculoDTO(List<RegistroEstacionamientoModel> listadoVehiculos, CodigoMensajeEnum codigoMensaje) {
        super(codigoMensaje);
        this.listadoVehiculos = listadoVehiculos;
    }
    
    public List<RegistroEstacionamientoModel> getListadoVehiculos() {
        return listadoVehiculos;
    }

    public void setListadoVehiculos(List<RegistroEstacionamientoModel> listadoVehiculos) {
        this.listadoVehiculos = listadoVehiculos;
    }

}
