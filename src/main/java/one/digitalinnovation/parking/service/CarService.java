package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.exception.CarNotFoundException;
import one.digitalinnovation.parking.exception.LicenseAlreadyRegisteredException;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car create(Car car) {
        boolean licenseExists = carRepository.findByLicense(car.getLicense()).isPresent();
        if (licenseExists) throw new LicenseAlreadyRegisteredException(car.getLicense());

        car.setIsParked(false);
        return carRepository.save(car);
    }

    public Car findByLicense(String license) {
        return carRepository.findByLicense(license).orElseThrow(() -> new CarNotFoundException(license));
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }
}
