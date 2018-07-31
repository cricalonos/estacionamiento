package co.com.ceiba.estacionamiento.util;

import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class FechaUtil {

    public boolean validarDiaDeLaSemana(Calendar fecha) {
        int diaDeLaSemana = fecha.get(Calendar.DAY_OF_WEEK);
        return (diaDeLaSemana == Calendar.MONDAY || diaDeLaSemana == Calendar.SUNDAY);
    }

    public Calendar obtenerFechaActual() {
        return Calendar.getInstance();
    }

}
