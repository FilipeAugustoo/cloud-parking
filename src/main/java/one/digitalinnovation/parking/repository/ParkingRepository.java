package one.digitalinnovation.parking.repository;

import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {

    List<Car> findByCarsLicense(String license);
}
