package co.com.ceiba.estacionamiento.cupo;

import java.math.BigDecimal;

public interface InfoVehiculo {

    int getMaximoCupo();

    BigDecimal getValorHora();

    BigDecimal getValorDia();
}
