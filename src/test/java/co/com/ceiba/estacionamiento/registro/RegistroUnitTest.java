package co.com.ceiba.estacionamiento.registro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.builder.VehiculoBuilder;
import co.com.ceiba.estacionamiento.converter.VehiculoConverter;
import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import co.com.ceiba.estacionamiento.service.VehiculoService;
import co.com.ceiba.estacionamiento.service.impl.EstacionamientoServiceImpl;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;
import co.com.ceiba.estacionamiento.util.Constantes;
import co.com.ceiba.estacionamiento.util.FechaUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistroUnitTest {

    @Mock
    VehiculoService vehiculoService;

    @Mock
    EstacionamientoRepository estacionamientoRepository;

    @Mock
    VehiculoConverter vehiculoConverter;

    @Mock
    FechaUtil fechaUtil;

    @InjectMocks
    EstacionamientoServiceImpl estacionamientoService = new EstacionamientoServiceImpl(vehiculoService,
            estacionamientoRepository, vehiculoConverter, fechaUtil);

    @Test
    public void registrarIngresoSatisfactorioTest() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().build();
        when(estacionamientoRepository.countByFechaSalidaAndVehiculoTipoVehiculo(any(), any())).thenReturn(0);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        Calendar fecha = Calendar.getInstance();
        fecha.set(2018, Calendar.JULY, 24);
        when(fechaUtil.obtenerFechaActual()).thenReturn(fecha);
        try {
            // Act
            RespuestaDTO respuesta = estacionamientoService.registrarIngresoAlEstacionamiento(vehiculo);
            // Assert
            assertEquals(CodigoMensajeEnum.EXITO.getCodigo(), respuesta.getCodigo());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void falloPorCupoDeCarros() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().conTipo(TipoVehiculoEnum.CARRO).build();

        when(estacionamientoRepository.countByFechaSalidaAndVehiculoTipoVehiculo(any(), any()))
                .thenReturn(Constantes.CAPACIDAD_MAXIMA_CARROS);
        try {
            // Act
            estacionamientoService.registrarIngresoAlEstacionamiento(vehiculo);
            fail();
        } catch (EstacionamientoException e) {
            // Assert
            assertEquals(CodigoMensajeEnum.NO_HAY_ESPACIO_DISPONIBLE.getCodigo(), e.getCodigo());
        }
    }

    @Test
    public void falloPorCupoDeMotos() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).build();
        when(estacionamientoRepository.countByFechaSalidaAndVehiculoTipoVehiculo(any(), any()))
                .thenReturn(Constantes.CAPACIDAD_MAXIMA_MOTOS);
        try {
            // Act
            estacionamientoService.registrarIngresoAlEstacionamiento(vehiculo);
            fail();
        } catch (EstacionamientoException e) {
            // Assert
            assertEquals(CodigoMensajeEnum.NO_HAY_ESPACIO_DISPONIBLE.getCodigo(), e.getCodigo());
        }
    }

    @Test
    public void falloPorLetraDePlaca() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().build();
        when(estacionamientoRepository.countByFechaSalidaAndVehiculoTipoVehiculo(any(), any())).thenReturn(0);
        Calendar fecha = Calendar.getInstance();
        fecha.set(2018, 6, 29);
        when(fechaUtil.obtenerFechaActual()).thenReturn(Calendar.getInstance());
        try {
            // Act
            estacionamientoService.registrarIngresoAlEstacionamiento(vehiculo);
            fail();
        } catch (EstacionamientoException e) {
            // Assert
            assertEquals(CodigoMensajeEnum.NO_AUTORIZADO_PARA_INGRESO.getCodigo(), e.getCodigo());
        }
    }

    @Test
    public void falloVehiculoYaEstacionado() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().build();
        when(estacionamientoRepository.countByFechaSalidaAndVehiculoTipoVehiculo(any(), any())).thenReturn(0);
        when(estacionamientoRepository.findByVehiculoPlaca(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            estacionamientoService.registrarIngresoAlEstacionamiento(vehiculo);
            fail();
        } catch (EstacionamientoException e) {
            // Assert
            assertEquals(CodigoMensajeEnum.VEHICULO_YA_ESTACIONADO.getCodigo(), e.getCodigo());
        }
    }

}
