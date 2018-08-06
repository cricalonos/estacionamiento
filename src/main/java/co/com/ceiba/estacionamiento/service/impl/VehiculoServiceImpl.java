package co.com.ceiba.estacionamiento.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.converter.VehiculoConverter;
import co.com.ceiba.estacionamiento.entity.Vehiculo;
import co.com.ceiba.estacionamiento.model.VehiculoModel;
import co.com.ceiba.estacionamiento.repository.VehiculoRepository;
import co.com.ceiba.estacionamiento.service.VehiculoService;

@Service("vehiculoService")
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    @Qualifier("vehiculoRepository")
    VehiculoRepository vehiculoRepository;

    @Autowired
    @Qualifier("vehiculoConverter")
    VehiculoConverter vehiculoConverter;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, VehiculoConverter vehiculoConverter) {
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoConverter = vehiculoConverter;
    }

    /**
     * Método empleado para verificar si un vehículo está registrado, si no está registrado procede a registrarlo.
     * 
     * @param vehiculoModel vehículo a verificar.
     * @return Entidad relacionada al vehículo verificado.
     */
    public Vehiculo verificarVehiculo(VehiculoModel vehiculoModel) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(vehiculoModel.getPlaca());

        if (vehiculo == null) {
            vehiculo = vehiculoRepository.save(vehiculoConverter.convertirModeloAEntidad(vehiculoModel));
        }

        return vehiculo;
    }

    /**
     * Método encargado de consultar un vehículo por la placa.
     * 
     * @param placa Placa del vehículo a consultar.
     * @return Modelo del vehículo encontrado.
     */
    public VehiculoModel consultarVehiculoPorPlaca(String placa) {
        return vehiculoConverter.convertirEntidadAModelo(vehiculoRepository.findByPlaca(placa));
    }

}
