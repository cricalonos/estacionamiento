package co.com.ceiba.estacionamiento.registro;

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
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.builder.VehiculoBuilder;
import co.com.ceiba.estacionamiento.dto.RespuestaDTO;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistroIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void registrarIngresoSatisfactorioTest() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().conPlaca("WWW121").build();

        // Act
        ResponseEntity<RespuestaDTO> repuesta = restTemplate.postForEntity("/registrarIngreso", vehiculo,
                RespuestaDTO.class);
        RespuestaDTO dto = repuesta.getBody();

        // Assert
        assertEquals(HttpStatus.OK, repuesta.getStatusCode());
        assertEquals(CodigoMensajeEnum.EXITO.getCodigo(), dto.getCodigo());
    }

    @Test
    public void falloVehiculoDobleTest() {
        // Arrange
        VehiculoModel vehiculo = new VehiculoBuilder().conPlaca("QWERTY").build();
        restTemplate.postForEntity("/registrarIngreso", vehiculo, RespuestaDTO.class);
        // Act
        ResponseEntity<RespuestaDTO> repuesta = restTemplate.postForEntity("/registrarIngreso", vehiculo,
                RespuestaDTO.class);
        RespuestaDTO dto = repuesta.getBody();
        // Assert
        assertEquals(HttpStatus.OK, repuesta.getStatusCode());
        assertEquals(CodigoMensajeEnum.VEHICULO_YA_ESTACIONADO.getCodigo(), dto.getCodigo());
    }

}
