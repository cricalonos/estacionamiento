package co.com.ceiba.estacionamiento.consulta;

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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.dto.ConsultaVehiculoDTO;
import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConsultaIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SqlGroup({
            @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:consultarVehiculosSatisfactorio.sql"),
            @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:eliminarRegistros.sql") })
    public void consultarVehiculosSatisfactorioTest() {
        // Act
        ResponseEntity<ConsultaVehiculoDTO> repuesta = restTemplate.getForEntity("/consultarVehiculos",
                ConsultaVehiculoDTO.class);
        ConsultaVehiculoDTO dto = repuesta.getBody();

        // Assert
        assertEquals(HttpStatus.OK, repuesta.getStatusCode());
        assertEquals(CodigoMensajeEnum.CONSULTA_EXITOSA.getCodigo(), dto.getCodigo());
        assertEquals(false, dto.getListadoVehiculos().isEmpty());
    }

    @Test
    @SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:eliminarRegistros.sql"))
    public void falloConsultarVehiculosTest() {

        // Act
        ResponseEntity<RespuestaDTO> repuesta = restTemplate.getForEntity("/consultarVehiculos", RespuestaDTO.class);
        RespuestaDTO dto = repuesta.getBody();

        // Assert
        assertEquals(HttpStatus.OK, repuesta.getStatusCode());
        assertEquals(CodigoMensajeEnum.NO_VEHICULOS_ESTACIONADOS.getCodigo(), dto.getCodigo());
    }

}
