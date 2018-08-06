package co.com.ceiba.estacionamiento.cupo;

import java.math.BigDecimal;

import co.com.ceiba.estacionamiento.util.Constantes;

public class InfoCarro implements InfoVehiculo {

    public int getMaximoCupo() {
        return Constantes.CAPACIDAD_MAXIMA_CARROS;
    }

    public BigDecimal getValorHora() {
        return Constantes.VALOR_HORA_CARRO;
    }

    public BigDecimal getValorDia() {
        return Constantes.VALOR_DIA_CARRO;
    }

}
