package co.com.ceiba.estacionamiento.builder;

import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;
import co.com.ceiba.estacionamiento.model.VehiculoModel;

public class VehiculoBuilder {

    private String placa;
    private String tipo;
    private Integer cilindraje;

    public VehiculoBuilder() {
        this.placa = "XEH85D";
        this.tipo = "CARRO";
        this.cilindraje = 0;
    }

    public VehiculoBuilder conTipo(TipoVehiculoEnum tipo) {
        this.tipo = tipo.toString();
        return this;
    }

    public VehiculoBuilder conCilindraje(Integer cilindraje) {
        this.cilindraje = cilindraje;
        return this;
    }

    public VehiculoBuilder conPlaca(String placa) {
        this.placa = placa;
        return this;
    }

    public VehiculoModel build() {
        return new VehiculoModel(placa, tipo, cilindraje);
    }

}
