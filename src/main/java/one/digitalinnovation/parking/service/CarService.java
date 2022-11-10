package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.controller.mapper.CarMapper;
import one.digitalinnovation.parking.exception.CarNotFoundException;
import one.digitalinnovation.parking.exception.FullParkingException;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final ParkingService service;
    private final CarRepository carRepository;
    private final CarMapper mapper;

    public Car create(CarCreateDTO dto) {
        var parking = service.findParking();
        Car car = mapper.toCar(dto);
        car.setEstaEstacionado(true);
        car.setEntryDate(LocalDateTime.now());
        parking.entryCar(car);

        if (parking.getVAGAS_OCUPADAS() == parking.getQUANT_VAGAS())
            throw new FullParkingException("Estacionamento Cheio");

        return carRepository.save(car);
    }

    public Car exit(String license) {
        Car car = findByLicense(license);
        var parking = service.findParking();
        car.setExitDate(LocalDateTime.now());
        car.setBill(ParkingCheckOut.getBill(car));
        car.setEstaEstacionado(false);
        parking.exitCar(car);

        return car;
    }

    public Car findByLicense(String license) {
        return carRepository.findByLicense(license).orElseThrow(() -> new CarNotFoundException(license));
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }
}
