package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository repository;
    @Value("${parking.id}")
    private String ID;

    @Transactional(readOnly = true)
    public Parking findParking() {
        return repository.findById(ID).orElseThrow(() -> new ParkingNotFoundException(ID));
    }

}
