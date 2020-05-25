package pl.pszczolkowski.teai2.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pszczolkowski.teai2.model.Vehicle;
import pl.pszczolkowski.teai2.service.VehicleService;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "https://vehicles-angular.herokuapp.com/")
public class VehicleApi {


    private VehicleService vehicleService;

    @Autowired
    public VehicleApi(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Vehicle>> getVehicles(){
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(allVehicles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long id){
//        Link link = linkTo(VehicleApi.class).slash(id).withSelfRel();
        Optional<Vehicle> vehicleById = vehicleService.getVehicleByIdSer(id);
        if (vehicleById.isPresent()){
            return new ResponseEntity<>(vehicleById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/colors/{color}")
    public ResponseEntity<List<Vehicle>> getVehicleByColor(@PathVariable String color){
        List<Vehicle> tempList = vehicleService.getVehicleByColorSer(color);
        if (!tempList.isEmpty()){
            return new ResponseEntity<>(tempList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle){
        vehicleService.addVehicleSer(vehicle);
        if (vehicleService.getAllVehicles().contains(vehicle)){
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Vehicle> modifyById(@RequestParam long id, @RequestBody Vehicle vehicleMod) {
        Optional<Vehicle> first = vehicleService.modifyByIdSer(id);
        if (first.isPresent()){
            vehicleService.getAllVehicles().remove(first.get());
            vehicleService.getAllVehicles().add(vehicleMod);
            return new ResponseEntity<>(vehicleMod, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping
    public ResponseEntity<Vehicle> modifyByIdOneParam(@RequestParam long id, @RequestBody Vehicle vehicleParam){
        Optional<Vehicle> first = vehicleService.findById(id);
        if (first.isPresent()){
            vehicleService.modifyByIdOneParamSer(vehicleParam, first);
            return new ResponseEntity<>(vehicleParam, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<Vehicle> deleteVehicleById(@RequestParam long id){
        Optional<Vehicle> first = vehicleService.findById(id);
        if (first.isPresent()){
            vehicleService.getAllVehicles().remove(first.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
