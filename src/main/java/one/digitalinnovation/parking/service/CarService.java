package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.controller.mapper.CarMapper;
import one.digitalinnovation.parking.exception.CarNotFoundException;
import one.digitalinnovation.parking.exception.FullParkingException;
import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.CarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final ParkingService service;
    private final CarRepository carRepository;
    private final CarMapper mapper;
    @Value("${parking.id}")
    private String id;

    public Car create(CarCreateDTO dto) {
        var parking = service.findById(id);
        parking.inputParking();
        Car car = mapper.toCar(dto);
        car.setParking(parking);
        car.setEntryDate(LocalDateTime.now());

        if (Parking.QUANT_VAGAS == Parking.LIMITE_VAGAS) throw new FullParkingException("Estacionamento Cheio");

        return carRepository.save(car);
    }

    public Car exit(String license) {
        Optional<Car> optCar = carRepository.findByLicense(license);
        var parking = service.findById(id);

        if (optCar.isEmpty()) throw new ParkingNotFoundException(license);

        Car car = optCar.get();
        car.setExitDate(LocalDateTime.now());
        car.setBill(ParkingCheckOut.getBill(car));
        parking.outputParking();

        return car;
    }

    public Car findByLicense(String license) {
        return carRepository.findByLicense(license).orElseThrow(() -> new CarNotFoundException(license));
    }
}
