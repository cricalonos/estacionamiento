package co.com.ceiba.estacionamiento.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.cupo.FactoryInfo;
import co.com.ceiba.estacionamiento.cupo.InfoVehiculo;
import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.dto.SalidaVehiculoDTO;
import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import co.com.ceiba.estacionamiento.service.SalidaVehiculoService;
import co.com.ceiba.estacionamiento.service.VehiculoService;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;
import co.com.ceiba.estacionamiento.util.Constantes;
import co.com.ceiba.estacionamiento.util.FechaUtil;

@Service("salidaVehiculoService")
public class SalidaVehiculoServiceImpl implements SalidaVehiculoService {

    @Autowired
    @Qualifier("estacionamientoRepository")
    private EstacionamientoRepository estacionamientoRepository;

    @Autowired
    @Qualifier("vehiculoService")
    private VehiculoService vehiculoService;

    @Autowired
    private FechaUtil fechaUtil;

    /**
     * M�todo constructor de la clase.
     * 
     * @param estacionamientoRepository Utilizado para acceder a base de datos.
     * @param vehiculoService           Utilizado para acceder a las funciones propias del veh�culo.
     * @param fechaUtil                 Utilizado para acceder a las funciones de fecha.
     */
    public SalidaVehiculoServiceImpl(EstacionamientoRepository estacionamientoRepository,
            VehiculoService vehiculoService, FechaUtil fechaUtil) {
        super();
        this.estacionamientoRepository = estacionamientoRepository;
        this.vehiculoService = vehiculoService;
        this.fechaUtil = fechaUtil;
    }

    /**
     * M�todo encargado de registrar la salida de un veh�culo del estacionamiento.
     * 
     * @param placa Placa del veh�culo que saldr�.
     * @return DTO con el resultado de la operaci�n.
     * @throws EstacionamientoException Lanzada si ocurre alg�n durante el proceso.
     */
    public RespuestaDTO registrarSalidaVehiculo(String placa) throws EstacionamientoException {
        RegistroEstacionamiento registro = estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(placa);
        if (registro == null)
            throw new EstacionamientoException(CodigoMensajeEnum.VEHICULO_NO_ESTACIONADO);
        long totalHoras = fechaUtil.calcularHorasEstacionado(registro.getFechaIngreso());
        VehiculoModel vehiculo = vehiculoService.consultarVehiculoPorPlaca(placa);
        BigDecimal costoTotal = calcularTiempo(totalHoras, vehiculo.getTipo());
        if (vehiculo.getTipo().equals(TipoVehiculoEnum.MOTO.toString())
                && vehiculo.getCilindraje() > Constantes.CIILINDRAJE_MOTO)
            costoTotal = costoTotal.add(Constantes.COSTO_CILINDRAJE_MOTO);
        registro.setFechaSalida(fechaUtil.obtenerFechaActual().getTime());
        registro.setCosto(costoTotal);
        if (estacionamientoRepository.save(registro) == null)
            throw new EstacionamientoException(CodigoMensajeEnum.ERROR);
        return new SalidaVehiculoDTO(vehiculo.getPlaca(), costoTotal, CodigoMensajeEnum.SALIDA_EXITOSA);
    }

    /**
     * M�todo encargado de calcular el tiempo total y su costo.
     * 
     * @param horas        N�mero de horas que dur� el veh�culo estacionado.
     * @param tipoVehiculo Tipo de veh�sulo estacionado.
     * @return Costo del tiempo estacionado.
     * @throws EstacionamientoException Lanzado en caso de recibir un tipo no v�lido.
     */
    private static BigDecimal calcularTiempo(Long horas, String tipoVehiculo) throws EstacionamientoException {
        if (horas < Constantes.NUMERO_HORAS_DEL_DIA) {
            return calculcarCosto(horas, Constantes.NUMERO_CERO, tipoVehiculo);
        }
        if (horas < Constantes.HORAS_DEL_DIA) {
            return calculcarCosto(Constantes.NUMERO_CERO, Constantes.NUMERO_UNO, tipoVehiculo);
        }
        Long dias = horas / Constantes.HORAS_DEL_DIA;
        Long totalHoras = Constantes.NUMERO_CERO;
        if (Math.floor(dias) > Constantes.NUMERO_CERO) {
            totalHoras = horas % Constantes.HORAS_DEL_DIA;
            if (totalHoras > Constantes.NUMERO_HORAS_DEL_DIA) {
                dias++;
                totalHoras = Constantes.NUMERO_CERO;
            }
        }
        return calculcarCosto(totalHoras, dias, tipoVehiculo);
    }

    /**
     * M�todo empleado para calcular el costo del tiempo estacionado.
     * 
     * @param horas        N�mero de horas estacionado.
     * @param dias         N�mero de d�as estacionado.
     * @param tipoVehiculo Tipo de veh�culo estacionado.
     * @return Costo del tiempo estacionado.
     * @throws EstacionamientoException Lanzado en caso de recibir un tipo no v�lido.
     */
    private static BigDecimal calculcarCosto(Long horas, Long dias, String tipoVehiculo)
            throws EstacionamientoException {
        BigDecimal totalCosto;
        TipoVehiculoEnum tipo = TipoVehiculoEnum.valueOf(tipoVehiculo);
        InfoVehiculo infoVehiculo = FactoryInfo.getInfo(tipo);
        totalCosto = infoVehiculo.getValorDia().multiply(new BigDecimal(dias));
        totalCosto = totalCosto.add(infoVehiculo.getValorHora().multiply(new BigDecimal(horas)));
        return totalCosto;
    }
}
