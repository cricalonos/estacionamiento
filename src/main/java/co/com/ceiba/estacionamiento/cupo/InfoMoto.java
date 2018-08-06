package co.com.ceiba.estacionamiento.cupo;

import java.math.BigDecimal;

import co.com.ceiba.estacionamiento.util.Constantes;

public class InfoMoto implements InfoVehiculo {

    public int getMaximoCupo() {
        return Constantes.CAPACIDAD_MAXIMA_MOTOS;
    }

    public BigDecimal getValorHora() {
        return Constantes.VALOR_HORA_MOTO;
    }

    public BigDecimal getValorDia() {
        return Constantes.VALOR_DIA_MOTO;
    }

}
