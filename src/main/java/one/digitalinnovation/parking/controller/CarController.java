package one.digitalinnovation.parking.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.controller.mapper.CarMapper;
import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.CarRepository;
import one.digitalinnovation.parking.repository.ParkingRepository;
import one.digitalinnovation.parking.service.ParkingCheckOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarRepository repository;
    private final ParkingRepository parkingRepository;
    @Value("${parking.id}")
    private String ID;
    private final Parking parking = parkingRepository.findById(ID).orElseThrow(() -> new ParkingNotFoundException(ID));
    private final CarMapper mapper;


    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody CarCreateDTO dto) {
        parking.inputParking();
        Car car = mapper.toCar(dto);
        car.setParking(parking);
        car.setEntryDate(LocalDateTime.now());
        repository.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("/{license}")
    public ResponseEntity<Car> exitCar(@PathVariable String license) {
        Optional<Car> optCar = repository.findByLicense(license);

        if (optCar.isEmpty()) return ResponseEntity.notFound().build();

        Car car = optCar.get();
        car.setExitDate(LocalDateTime.now());
        car.setBill(ParkingCheckOut.getBill(car));
        parking.outputParking();

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }
}
