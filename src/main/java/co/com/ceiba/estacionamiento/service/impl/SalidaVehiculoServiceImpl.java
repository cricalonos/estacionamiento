package co.com.ceiba.estacionamiento.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    public SalidaVehiculoServiceImpl(EstacionamientoRepository estacionamientoRepository,
            VehiculoService vehiculoService, FechaUtil fechaUtil) {
        super();
        this.estacionamientoRepository = estacionamientoRepository;
        this.vehiculoService = vehiculoService;
        this.fechaUtil = fechaUtil;
    }

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

    private static BigDecimal calcularTiempo(Long horas, String tipoVehiculo) {
        if (horas < Constantes.NUMERO_HORAS_DEL_DIA) {
            return calculcarCosto(horas, 0L, tipoVehiculo);
        }
        if (horas < 24) {
            return calculcarCosto(0L, 1L, tipoVehiculo);
        }
        Long dias = horas / 24L;
        Long totalHoras = 0L;
        if (Math.floor(dias) > 0) {
            totalHoras = horas % 24;
            if (totalHoras > Constantes.NUMERO_HORAS_DEL_DIA) {
                dias += 1;
                totalHoras = 0L;
            }
        }
        return calculcarCosto(totalHoras, dias, tipoVehiculo);
    }

    private static BigDecimal calculcarCosto(Long horas, Long dias, String tipoVehiculo) {
        BigDecimal totalCosto;
        if (TipoVehiculoEnum.MOTO.toString().equals(tipoVehiculo)) {
            totalCosto = Constantes.VALOR_DIA_MOTO.multiply(new BigDecimal(dias));
            totalCosto = totalCosto.add(Constantes.VALOR_HORA_MOTO.multiply(new BigDecimal(horas)));
        } else {
            totalCosto = Constantes.VALOR_DIA_CARRO.multiply(new BigDecimal(dias));
            totalCosto = totalCosto.add(Constantes.VALOR_HORA_CARRO.multiply(new BigDecimal(horas)));
        }
        return totalCosto;
    }
}
