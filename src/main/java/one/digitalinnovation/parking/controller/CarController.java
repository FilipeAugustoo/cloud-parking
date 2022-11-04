package one.digitalinnovation.parking.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody CarCreateDTO dto) {
        Car car = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("/{license}")
    public ResponseEntity<Car> exitCar(@PathVariable String license) {
        Car car = service.exit(license);
        return ResponseEntity.ok(car);
    }
}
