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

    public void removeCar(String license) {
        Car car = carRepository.findByLicense(license).orElseThrow(() -> new CarNotFoundException(license));
        Parking parking = findParking();
        parking.getCars().remove(car);

        System.out.println(parking.getCars());
    }


    public Parking findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
    }
}
