package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.exception.CarNotFoundException;
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
        return repository.findById(id).orElseThrow(() -> new RuntimeException(id));
    }

    public Car entryCar(String license) {
        Car car = carRepository.findByLicense(license).orElseThrow(() -> new CarNotFoundException(license));
        Parking parking = findParking();
        car.setAmountParked(1);
        car.setIsParked(true);
        car.setEntryDate(LocalDateTime.now());
        car.setExitDate(null);
        car.setBill(null);
        parking.entryCar(car);
        carRepository.save(car);
        return car;
    }

    public Car exitCar(String license) {
        var car = carRepository.findByLicense(license).orElseThrow(() -> new CarNotFoundException(license));
        var parking = findParking();
        car.setExitDate(LocalDateTime.now());
        car.setBill(ParkingCheckOut.getBill(car));
        car.setIsParked(false);
        parking.exitCar(car);
        carRepository.save(car);

        return car;
    }

}
