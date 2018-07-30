package co.com.ceiba.estacionamiento.model;

public class VehiculoModel {

    private String placa;
    private String tipo;
    private Integer cilindraje;

    public VehiculoModel() {
    }

    public VehiculoModel(String placa, String tipo, Integer cilindraje) {
        this.placa = placa;
        this.tipo = tipo;
        this.cilindraje = cilindraje;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(Integer cilindraje) {
        this.cilindraje = cilindraje;
    }

}
