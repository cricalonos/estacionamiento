package co.com.ceiba.estacionamiento.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import co.com.ceiba.estacionamiento.service.IngresoVehiculoService;
import co.com.ceiba.estacionamiento.service.VehiculoService;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;
import co.com.ceiba.estacionamiento.util.Constantes;
import co.com.ceiba.estacionamiento.util.FechaUtil;

@Service("ingresoVehiculoService")
public class IngresoVehiculoServiceImpl implements IngresoVehiculoService {

    @Autowired
    @Qualifier("estacionamientoRepository")
    private EstacionamientoRepository estacionamientoRepository;

    @Autowired
    @Qualifier("vehiculoService")
    private VehiculoService vehiculoService;

    @Autowired
    private FechaUtil fechaUtil;

    public IngresoVehiculoServiceImpl(VehiculoService vehiculoService,
            EstacionamientoRepository estacionamientoRepository, FechaUtil fechaUtil) {
        this.vehiculoService = vehiculoService;
        this.estacionamientoRepository = estacionamientoRepository;
        this.fechaUtil = fechaUtil;
    }

    public RespuestaDTO registrarIngresoAlEstacionamiento(VehiculoModel vehiculoModel) throws EstacionamientoException {

        if (!validarPlacaPorLetra(vehiculoModel.getPlaca()))
            throw new EstacionamientoException(CodigoMensajeEnum.NO_AUTORIZADO_PARA_INGRESO);

        if (!validarEspaciosDisponibles(vehiculoModel.getTipo()))
            throw new EstacionamientoException(CodigoMensajeEnum.NO_HAY_ESPACIO_DISPONIBLE);

        if (estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(vehiculoModel.getPlaca()) != null)
            throw new EstacionamientoException(CodigoMensajeEnum.VEHICULO_YA_ESTACIONADO);

        Vehiculo vehiculo = vehiculoService.verificarVehiculo(vehiculoModel);
        RegistroEstacionamiento estacionamiento = new RegistroEstacionamiento();
        estacionamiento.setVehiculo(vehiculo);
        estacionamiento.setFechaIngreso(Calendar.getInstance().getTime());
        RespuestaDTO respuesta;
        if (estacionamientoRepository.save(estacionamiento) != null) {
            respuesta = new RespuestaDTO(CodigoMensajeEnum.INGRESO_EXITOSO);
        } else {
            respuesta = new RespuestaDTO(CodigoMensajeEnum.ERROR);
        }
        return respuesta;
    }

    private boolean validarPlacaPorLetra(String placa) {
        boolean esValido = true;
        if (placa.startsWith("A")) {
            esValido = fechaUtil.validarDiaDeLaSemana(fechaUtil.obtenerFechaActual());
        }
        return esValido;
    }

    private boolean validarEspaciosDisponibles(String tipoVehiculo) {
        boolean hayEspacioDisponible = false;
        TipoVehiculoEnum tipo = TipoVehiculoEnum.valueOf(tipoVehiculo);
        Integer totalEstacionamientosUsados = estacionamientoRepository.countByFechaSalidaAndVehiculoTipoVehiculo(null,
                tipo);
        if (tipo == TipoVehiculoEnum.CARRO) {
            hayEspacioDisponible = totalEstacionamientosUsados < Constantes.CAPACIDAD_MAXIMA_CARROS;
        } else {
            hayEspacioDisponible = totalEstacionamientosUsados < Constantes.CAPACIDAD_MAXIMA_MOTOS;
        }
        return hayEspacioDisponible;
    }

}
