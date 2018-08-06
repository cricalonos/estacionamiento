package co.com.ceiba.estacionamiento.consulta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.converter.RegistroEstacionamentoConverter;
import co.com.ceiba.estacionamiento.dto.ConsultaVehiculoDTO;
import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.exception.EstacionamientoException;
import co.com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import co.com.ceiba.estacionamiento.service.impl.ConsultaVehiculoServiceImpl;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class ConsultaUnitTest {

    @Mock
    EstacionamientoRepository estacionamientoRepository;

    @Mock
    RegistroEstacionamentoConverter registroEstacionamentoConverter;

    @InjectMocks
    ConsultaVehiculoServiceImpl consultaVehiculosService = new ConsultaVehiculoServiceImpl(estacionamientoRepository);

    @Test
    public void consultaVehiculosSatisfactoriaTest() {
        // Arrange
        List<RegistroEstacionamiento> listaEntidades = new ArrayList<RegistroEstacionamiento>();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca("000");
        vehiculo.setTipoVehiculo(TipoVehiculoEnum.CARRO);
        listaEntidades.add(new RegistroEstacionamiento(1L, Calendar.getInstance().getTime(), null, null, vehiculo));
        when(estacionamientoRepository.findByFechaSalidaNull()).thenReturn(listaEntidades);
        when(registroEstacionamentoConverter.convertirEntidadAModelo(any())).thenCallRealMethod();
        when(registroEstacionamentoConverter.convertirListaEntidadAListaModelo(any())).thenCallRealMethod();
        try {
            // Act
            ConsultaVehiculoDTO consultaDTO = (ConsultaVehiculoDTO) consultaVehiculosService
                    .consultarVehiculosEstacionados();
            // Assert
            assertEquals(CodigoMensajeEnum.CONSULTA_EXITOSA.getCodigo(), consultaDTO.getCodigo());
        } catch (EstacionamientoException e) {
            fail();
        }
    }

    @Test
    public void falloConsultaVehiculosNoEstacionadosTest() {
        // Arrange
        when(estacionamientoRepository.findByFechaSalidaNull()).thenReturn(new ArrayList<RegistroEstacionamiento>());
        try {
            // Act
            consultaVehiculosService.consultarVehiculosEstacionados();
            fail();
        } catch (EstacionamientoException e) {
            // Assert
            assertEquals(CodigoMensajeEnum.NO_VEHICULOS_ESTACIONADOS.getCodigo(), e.getCodigo());
        }
    }

}
