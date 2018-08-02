package co.com.ceiba.estacionamiento.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.converter.RegistroEstacionamentoConverter;
import co.com.ceiba.estacionamiento.dto.ConsultaVehiculoDTO;
import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.RegistroEstacionamientoModel;
import co.com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import co.com.ceiba.estacionamiento.service.ConsultaVehiculoService;
import co.com.ceiba.estacionamiento.service.VehiculoService;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

@Service("consultaVehiculoService")
public class ConsultaVehiculoServiceImpl implements ConsultaVehiculoService {

    @Autowired
    @Qualifier("estacionamientoRepository")
    private EstacionamientoRepository estacionamientoRepository;

    @Autowired
    @Qualifier("vehiculoService")
    private VehiculoService vehiculoService;

    @Autowired
    @Qualifier("registroEstacionamentoConverter")
    private RegistroEstacionamentoConverter registroConverter;

    public ConsultaVehiculoServiceImpl(EstacionamientoRepository estacionamientoRepository,
            VehiculoService vehiculoService) {
        this.estacionamientoRepository = estacionamientoRepository;
        this.vehiculoService = vehiculoService;
    }

    public RespuestaDTO consultarVehiculosEstacionados() throws EstacionamientoException {
        List<RegistroEstacionamiento> listaEntidad = estacionamientoRepository.findByFechaSalidaNull();
        if (listaEntidad.isEmpty())
            throw new EstacionamientoException(CodigoMensajeEnum.NO_VEHICULOS_ESTACIONADOS);
        List<RegistroEstacionamientoModel> list = registroConverter.convertirListaEntidadAListaModelo(listaEntidad);
        return new ConsultaVehiculoDTO(list, CodigoMensajeEnum.CONSULTA_EXITOSA);
    }

}
