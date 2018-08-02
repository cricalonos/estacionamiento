package co.com.ceiba.estacionamiento.dto;

import java.math.BigDecimal;

import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

public class SalidaVehiculoDTO extends RespuestaDTO {

    private String placa;
    private BigDecimal costoTotal;

    public SalidaVehiculoDTO() {
        super();
    }

    public SalidaVehiculoDTO(String placa, BigDecimal costoTotal, CodigoMensajeEnum codigoMensaje) {
        super(codigoMensaje);
        this.placa = placa;
        this.costoTotal = costoTotal;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }
}
