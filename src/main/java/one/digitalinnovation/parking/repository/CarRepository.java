package one.digitalinnovation.parking.repository;

import one.digitalinnovation.parking.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findByLicense(String license);
}
