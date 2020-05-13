package pl.pszczolkowski.teai2.service;

import org.springframework.http.ResponseEntity;
import pl.pszczolkowski.teai2.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> getAllVehicles();
    Optional<Vehicle> getVehicleByIdSer(long id);
    List<Vehicle> getVehicleByColorSer(String color);
    void addVehicleSer(Vehicle vehicle);
    Optional<Vehicle> modifyByIdSer(long id);
    void modifyByIdOneParamSer(Vehicle vehicle, Optional<Vehicle> first);
    Optional<Vehicle> findById(long id);
}
