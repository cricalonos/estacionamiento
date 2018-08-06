package co.com.ceiba.estacionamiento.util;

import java.math.BigDecimal;

public final class Constantes {

    public static final Integer CAPACIDAD_MAXIMA_CARROS = 20;
    public static final Integer CAPACIDAD_MAXIMA_MOTOS = 10;
    public static final BigDecimal VALOR_HORA_CARRO = new BigDecimal(1000);
    public static final BigDecimal VALOR_HORA_MOTO = new BigDecimal(500);
    public static final BigDecimal VALOR_DIA_CARRO = new BigDecimal(8000);
    public static final BigDecimal VALOR_DIA_MOTO = new BigDecimal(4000);
    public static final Integer CIILINDRAJE_MOTO = 500;
    public static final BigDecimal COSTO_CILINDRAJE_MOTO = new BigDecimal(2000);
    public static final Integer NUMERO_HORAS_DEL_DIA = 9;
    public static final Long HORAS_DEL_DIA = 24L;
    public static final Long NUMERO_CERO = 0L;
    public static final Long NUMERO_UNO = 1L;

    private Constantes() {

    }
}
