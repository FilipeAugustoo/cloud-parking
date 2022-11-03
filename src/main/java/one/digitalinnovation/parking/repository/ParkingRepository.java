package one.digitalinnovation.parking.repository;

import one.digitalinnovation.parking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {

    Optional<Parking> findByLicense(String license);
}
