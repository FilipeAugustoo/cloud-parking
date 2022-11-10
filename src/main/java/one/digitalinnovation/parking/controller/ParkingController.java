package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.controller.mapper.CarMapper;
import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    @PostMapping
    public ResponseEntity<Car> entryCar(@RequestBody @Valid CarCreateDTO carDto) {
        Car car = carMapper.toCar(carDto);
        service.entryCar(car);

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("{license}")
    public ResponseEntity<Car> exitCar(@PathVariable String license) {
        Car car = service.exitCar(license);
        return ResponseEntity.ok(car);

    }
}
