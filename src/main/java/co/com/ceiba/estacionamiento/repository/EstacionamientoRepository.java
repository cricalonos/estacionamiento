package co.com.ceiba.estacionamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.estacionamiento.entity.RegistroEstacionamiento;
import co.com.ceiba.estacionamiento.entity.TipoVehiculoEnum;

public interface EstacionamientoRepository extends JpaRepository<RegistroEstacionamiento, Long> {

    public Integer countByFechaSalidaNullAndVehiculoTipoVehiculo(TipoVehiculoEnum tipoVehiculo);

    public RegistroEstacionamiento findByVehiculoPlacaAndFechaSalidaNull(String placa);

    public List<RegistroEstacionamiento> findByFechaSalidaNull();
}
