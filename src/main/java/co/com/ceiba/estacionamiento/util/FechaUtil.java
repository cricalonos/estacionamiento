package co.com.ceiba.estacionamiento.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public long calcularHorasEstacionado(Date horaIngreso) {
        Date actual = obtenerFechaActual().getTime();
        return TimeUnit.HOURS.convert(Math.abs(actual.getTime() - horaIngreso.getTime()), TimeUnit.MILLISECONDS) + 1;
    }

}
