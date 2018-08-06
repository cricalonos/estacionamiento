package co.com.ceiba.estacionamiento.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.cupo.FactoryInfo;
import co.com.ceiba.estacionamiento.cupo.InfoVehiculo;
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

    /**
     * Constructor de la clase.
     * 
     * @param vehiculoService           Servicios de los vehículos necesarios para acceder a base de datos.
     * @param estacionamientoRepository Repositorio para acceder a los registros del estacionamiento.
     * @param fechaUtil                 Utilitario empleado para el manejo de las fechas.
     */
    public IngresoVehiculoServiceImpl(VehiculoService vehiculoService,
            EstacionamientoRepository estacionamientoRepository, FechaUtil fechaUtil) {
        this.vehiculoService = vehiculoService;
        this.estacionamientoRepository = estacionamientoRepository;
        this.fechaUtil = fechaUtil;
    }

    /**
     * Método empleado para registrar un vehículo al estacionamiento
     * 
     * @param vehiculoModel Modelo con los datos necesarios para el registro.
     * @return DTO que contiene el resultado de la operación.
     * @throws EstacionamientoException En caso de algún fallo en el proceso.F
     */
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
            throw new EstacionamientoException(CodigoMensajeEnum.ERROR);
        }
        return respuesta;
    }

    /**
     * Método que valida la placa por letra.
     * 
     * @param placa Placa a validar.
     * @return True si la placa es valida, false en caso contrario.
     */
    private boolean validarPlacaPorLetra(String placa) {
        boolean esValido = true;
        if (placa.startsWith("A")) {
            esValido = fechaUtil.validarDiaDeLaSemana(fechaUtil.obtenerFechaActual());
        }
        return esValido;
    }

    /**
     * Método empleado para validar espacions disponibles en el estacionamiento.
     * 
     * @param tipoVehiculo Tipo de vehículo que se estacionará.
     * @return True si hay espacio disponible, false en caso contrario.
     * @throws EstacionamientoException Lanzada en caso de recibir un tipo no válido.
     */
    private boolean validarEspaciosDisponibles(String tipoVehiculo) throws EstacionamientoException {
        TipoVehiculoEnum tipo = TipoVehiculoEnum.valueOf(tipoVehiculo);
        InfoVehiculo infoVehiculo = FactoryInfo.getInfo(tipo);
        Integer totalEstacionamientosUsados = estacionamientoRepository
                .countByFechaSalidaNullAndVehiculoTipoVehiculo(tipo);
        return totalEstacionamientosUsados < infoVehiculo.getMaximoCupo();
    }

}
