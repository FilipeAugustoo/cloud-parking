package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.exception.CarNotFoundException;
import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.CarRepository;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository repository;
    private final CarRepository carRepository;
    @Value("${parking.id}")
    private String id;

    @Transactional(readOnly = true)
    public Parking findParking() {
        return repository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
    }

    public Car entryCar(Car car) {
        Parking parking = findParking();
        parking.entryCar(car);
        car.setEntryDate(LocalDateTime.now());
        car.setEstaEstacionado(true);
        car.setQuantEstacionou(1);
        carRepository.save(car);
        return car;
    }

    public Car exitCar(String license) {
        var car = carRepository.findByLicense(license).orElseThrow(() -> new CarNotFoundException(license));
        var parking = findParking();
        car.setExitDate(LocalDateTime.now());
        car.setBill(ParkingCheckOut.getBill(car));
        parking.exitCar(car);
        carRepository.save(car);

        return car;
    }

}
