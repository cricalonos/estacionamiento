package co.com.ceiba.estacionamiento.util;

public enum CodigoMensajeEnum {

    EXITO("00", "Acci�n realizada exitosamente."), ERROR("01", "Error realizando la acci�n."),
    NO_HAY_ESPACIO_DISPONIBLE("03", "No hay espacio disponible para el veh�culo."),
    NO_AUTORIZADO_PARA_INGRESO("04", "No est� autorizado a ingresar."),
    CILINDRAJE_ES_OBLIGATORIO("05", "Debe ingresar un cilindraje."),
    VEHICULO_YA_ESTACIONADO("06", "Este veh�culo ya se encuentra en el estacionamiento.");

    private String codigo;
    private String mensaje;

    CodigoMensajeEnum(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }
}
