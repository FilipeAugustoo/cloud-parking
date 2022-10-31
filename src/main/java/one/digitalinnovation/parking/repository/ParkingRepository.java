package one.digitalinnovation.parking.repository;

import one.digitalinnovation.parking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, String> {

}
