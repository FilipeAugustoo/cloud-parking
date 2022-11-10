package one.digitalinnovation.parking.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;

    @GetMapping
    public ResponseEntity<List<Car>> findAllCars() {
        List<Car> cars = service.findAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("{license}")
    public ResponseEntity<Car> findByLicense(@PathVariable String license) {
        Car car = service.findByLicense(license);
        return ResponseEntity.ok(car);
    }
}
