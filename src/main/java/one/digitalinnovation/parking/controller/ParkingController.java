package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.controller.mapper.CarMapper;
import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService service;
    private final CarMapper carMapper;
    private final ParkingMapper parkingMapper;

    @GetMapping
    @ApiOperation("Busca Estacionamento")
    public ResponseEntity<ParkingDTO> findParking() {
        ParkingDTO parking = parkingMapper.toParkingDTO(service.findParking());
        return ResponseEntity.ok(parking);
    }


    @PostMapping("/entry/{license}")
    public ResponseEntity<Car> entryCar(@PathVariable String license) {
        Car car = service.entryCar(license);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("/exit/{license}")
    public ResponseEntity<Car> exitCar(@PathVariable String license) {
        Car car = service.exitCar(license);
        return ResponseEntity.ok(car);

    }
}
