package co.com.ceiba.estacionamiento.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.model.RegistroEstacionamientoModel;

@Component("registroEstacionamentoConverter")
public class RegistroEstacionamentoConverter {

    public RegistroEstacionamientoModel convertirEntidadAModelo(RegistroEstacionamiento entidad) {
        RegistroEstacionamientoModel registroModel = new RegistroEstacionamientoModel();
        registroModel.setFechaIngreso(entidad.getFechaIngreso());
        registroModel.setPlaca(entidad.getVehiculo().getPlaca());
        registroModel.setTipo(entidad.getVehiculo().getTipoVehiculo().toString());
        return registroModel;
    }

    public List<RegistroEstacionamientoModel> convertirListaEntidadAListaModelo(
            List<RegistroEstacionamiento> listaEntidad) {
        List<RegistroEstacionamientoModel> listaModel = new ArrayList<>();
        for (RegistroEstacionamiento registroEntidad : listaEntidad) {
            listaModel.add(convertirEntidadAModelo(registroEntidad));
        }
        return listaModel;
    }

}
