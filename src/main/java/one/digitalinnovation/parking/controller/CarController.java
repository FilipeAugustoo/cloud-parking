package one.digitalinnovation.parking.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.controller.dto.CarDTO;
import one.digitalinnovation.parking.controller.mapper.CarMapper;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;
    private final CarMapper mapper;

    @GetMapping
    public ResponseEntity<List<CarDTO>> findAllCars() {
        List<Car> cars = service.findAllCars();
        List<CarDTO> carsDTO = mapper.toCarDTOList(cars);
        return ResponseEntity.ok(carsDTO);
    }

    @GetMapping("{license}")
    public ResponseEntity<CarDTO> findByLicense(@PathVariable String license) {
        Car car = service.findByLicense(license);
        CarDTO carDTO = mapper.toCarDTO(car);
        return ResponseEntity.ok(carDTO);
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarCreateDTO dto) {
        Car car = mapper.toCar(dto);
        Car carCreated = service.create(car);
        CarDTO carDTO = mapper.toCarDTO(carCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(carDTO);
    }
}
