package co.com.ceiba.estacionamiento.salida;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.builder.VehiculoBuilder;
import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.dto.SalidaVehiculoDTO;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalidaIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SqlGroup(@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:eliminarRegistros.sql"))
    public void registrarSalidaSatisfactoriaCarroTest() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().conTipo(TipoVehiculoEnum.CARRO).conPlaca("WQW121").build();
        restTemplate.postForEntity("/registrarIngreso", vehiculo, RespuestaDTO.class);

        // Act
        ResponseEntity<SalidaVehiculoDTO> repuesta = restTemplate.getForEntity("/registrarSalida/{placa}",
                SalidaVehiculoDTO.class, vehiculo.getPlaca());
        SalidaVehiculoDTO dto = repuesta.getBody();

        // Assert
        assertEquals(HttpStatus.OK, repuesta.getStatusCode());
        assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), dto.getCodigo());
    }

    @Test
    @SqlGroup(@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:eliminarRegistros.sql"))
    public void registrarSalidaSatisfactoriaMotoTest() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().conTipo(TipoVehiculoEnum.MOTO).conPlaca("WWW121").build();
        restTemplate.postForEntity("/registrarIngreso", vehiculo, RespuestaDTO.class);

        // Act
        ResponseEntity<RespuestaDTO> repuesta = restTemplate.getForEntity("/registrarSalida/{placa}",
                RespuestaDTO.class, vehiculo.getPlaca());
        RespuestaDTO dto = repuesta.getBody();

        // Assert
        assertEquals(HttpStatus.OK, repuesta.getStatusCode());
        assertEquals(CodigoMensajeEnum.SALIDA_EXITOSA.getCodigo(), dto.getCodigo());
    }

    @Test
    @SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:eliminarRegistros.sql"))
    public void falloSalidaCarroNoEstacionadoTest() {
        // Arrange
        String placa = "AAA000";

        // Act
        ResponseEntity<RespuestaDTO> repuesta = restTemplate.getForEntity("/registrarSalida/{placa}",
                RespuestaDTO.class, placa);
        RespuestaDTO dto = repuesta.getBody();

        // Assert
        assertEquals(HttpStatus.OK, repuesta.getStatusCode());
        assertEquals(CodigoMensajeEnum.VEHICULO_NO_ESTACIONADO.getCodigo(), dto.getCodigo());
    }
}
