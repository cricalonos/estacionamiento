package co.com.ceiba.estacionamiento.exception;

import co.com.ceiba.estacionamiento.util.CodigoMensajeEnum;

public class EstacionamientoException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String codigo;
    private final String mensaje;

    public EstacionamientoException(CodigoMensajeEnum error) {
        super();
        this.codigo = error.getCodigo();
        this.mensaje = error.getMensaje();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

}
