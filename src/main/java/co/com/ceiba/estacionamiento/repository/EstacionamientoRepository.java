package co.com.ceiba.estacionamiento.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;

public interface EstacionamientoRepository extends JpaRepository<RegistroEstacionamiento, Long> {

    public Integer countByFechaSalidaAndVehiculoTipoVehiculo(Date fechaSalida, TipoVehiculoEnum tipoVehiculo);

    public RegistroEstacionamiento findByVehiculoPlaca(String placa);
}
