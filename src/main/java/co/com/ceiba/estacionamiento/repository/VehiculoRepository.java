package co.com.ceiba.estacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.entity.Vehiculo;

@Repository("vehiculoRepository")
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

    public Vehiculo findByPlaca(String placa);

}
