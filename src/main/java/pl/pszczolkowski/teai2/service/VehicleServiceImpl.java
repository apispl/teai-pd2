package pl.pszczolkowski.teai2.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.pszczolkowski.teai2.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    List<Vehicle> vehicleList = new ArrayList<>();

    public List<Vehicle> createListOfCars(){
        vehicleList.add(new Vehicle(1L, "BMW", "E36", "RED"));
        vehicleList.add(new Vehicle(2L, "Fiat", "126p", "BLUE"));
        vehicleList.add(new Vehicle(3L, "Kia", "Ceed", "GREY"));
        vehicleList.add(new Vehicle(4L, "BMW", "E90", "RED"));
        return vehicleList;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleList;
    }

    @Override
    public Optional<Vehicle> getVehicleByIdSer(long id) {
        return findById(id);
    }

    @Override
    public List<Vehicle> getVehicleByColorSer(String color) {
        return vehicleList
                .stream()
                .filter(vehicle -> vehicle.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public void addVehicleSer(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    @Override
    public Optional<Vehicle> modifyByIdSer(long id) {
        return findById(id);
    }

    @Override
    public void modifyByIdOneParamSer(Vehicle vehicle, Optional<Vehicle> first) {
        if (vehicle.getId() != first.get().getId()){
            first.get().setId(vehicle.getId());
        }
        if (!vehicle.getColor().equals(first.get().getColor())){
            first.get().setColor(vehicle.getColor());
        }
        if (!vehicle.getMark().equals(first.get().getMark())){
            first.get().setMark(vehicle.getMark());
        }
        if (!vehicle.getModel().equals(first.get().getModel())){
            first.get().setModel(vehicle.getModel());
        }
    }

    @Override
    public Optional<Vehicle> findById(long id) {
        return vehicleList
                .stream()
                .filter(vehicle -> vehicle.getId() == id)
                .findFirst();
    }


}
