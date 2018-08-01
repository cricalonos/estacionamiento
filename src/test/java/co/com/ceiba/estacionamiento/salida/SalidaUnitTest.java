package co.com.ceiba.estacionamiento.salida;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.builder.VehiculoBuilder;
import co.com.ceiba.estacionamiento.converter.VehiculoConverter;
import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.dto.SalidaVehiculoDTO;
import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import co.com.ceiba.estacionamiento.service.VehiculoService;
import co.com.ceiba.estacionamiento.service.impl.SalidaVehiculoServiceImpl;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;
import co.com.ceiba.estacionamiento.util.Constantes;
import co.com.ceiba.estacionamiento.util.FechaUtil;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class SalidaUnitTest {

    @Mock
    VehiculoService vehiculoService;

    @Mock
    EstacionamientoRepository estacionamientoRepository;

    @Mock
    VehiculoConverter vehiculoConverter;

    @Mock
    FechaUtil fechaUtil;

    @InjectMocks
    SalidaVehiculoServiceImpl salidaService = new SalidaVehiculoServiceImpl(estacionamientoRepository, vehiculoService,
            fechaUtil);

    @Test
    public void registrarSalidaSatisfactoriaTest() {
        // Arrange
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento());
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(new VehiculoBuilder().build());
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            RespuestaDTO respuesta = salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaCarroDosDiasSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.CARRO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(36L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(new BigDecimal(16000), respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaMotoDosDiasSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(36L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(new BigDecimal(8000), respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaCarroConDiaYHoraSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.CARRO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(28L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(new BigDecimal(12000), respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaCarroConDiaSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.CARRO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(11L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(Constantes.VALOR_DIA_CARRO, respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaCarroConHorasSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.CARRO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(1L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(Constantes.VALOR_HORA_CARRO, respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaMotoConDiaYHoraSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(28L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(new BigDecimal(6000), respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaMotoConDiaSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(11L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(Constantes.VALOR_DIA_MOTO, respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaMotoConHorasSatisfactoriaTest() {
        // Arrange
        VehiculoModel vehiculoModel = new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).build();
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, new BigDecimal(0),
                        new Vehiculo()));
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(vehiculoModel);
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(fechaUtil.calcularHorasEstacionado(any())).thenReturn(1L);
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(Constantes.VALOR_HORA_MOTO, respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaMotoSinCilindrajeTest() {
        // Arrange
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento());
        when(vehiculoService.consultarVehiculoPorPlaca(any()))
                .thenReturn(new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).build());
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(new BigDecimal(0), respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void registrarSalidaMotoConCilindrajeTest() {
        // Arrange
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento());
        when(vehiculoService.consultarVehiculoPorPlaca(any()))
                .thenReturn(new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).conCilindraje(501).build());
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        when(estacionamientoRepository.save(any())).thenReturn(new RegistroEstacionamiento());
        try {
            // Act
            SalidaVehiculoDTO respuesta = (SalidaVehiculoDTO) salidaService.registrarSalidaVehiculo("ASDASD");
            // Assert
            assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), respuesta.getCodigo());
            assertEquals(Constantes.COSTO_CILINDRAJE_MOTO, respuesta.getCostoTotal());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void falloActualizandoRegistroSalidaTest() {
        // Arrange
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any()))
                .thenReturn(new RegistroEstacionamiento());
        when(vehiculoService.consultarVehiculoPorPlaca(any())).thenReturn(new VehiculoBuilder().build());
        when(fechaUtil.obtenerFechaActual()).thenCallRealMethod();
        try {
            // Act
            salidaService.registrarSalidaVehiculo("ASDASD");
            fail();
        } catch (EstacionamientoException e) {
            // Assert
            assertEquals(CodigoMensajeEnum.ERROR.getCodigo(), e.getCodigo());
        }
    }

    @Test
    public void falloVehiculoNoEncontrado() {
        // Arrange
        when(estacionamientoRepository.findByVehiculoPlacaAndFechaSalidaNull(any())).thenReturn(null);
        try {
            // Act
            salidaService.registrarSalidaVehiculo("ASDASD");
            fail();
        } catch (EstacionamientoException e) {
            // Assert
            assertEquals(CodigoMensajeEnum.VEHICULO_NO_ESTACIONADO.getCodigo(), e.getCodigo());
        }
    }

}
