package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.exception.LicenseAlreadyRegisteredException;
import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository repository;

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Parking findByLicense(String id) {
        return repository.findByLicense(id).orElseThrow(() -> new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parking) {
        String uuid = getUUID();
        parking.setId(uuid);
        parking.setEntryDate(LocalDateTime.now());

        boolean placaExiste = repository.findByLicense(parking.getLicense()).isPresent();
        if (placaExiste) throw new LicenseAlreadyRegisteredException(parking.getLicense());

        repository.save(parking);
        return parking;
    }

    @Transactional
    public void delete(String license) {
        Parking parking = findByLicense(license);
        repository.deleteById(parking.getId());
    }

    @Transactional
    public Parking update(String license, Parking parkingCreate) {
        Parking parking = findByLicense(license);
        parking.setColor(parkingCreate.getColor());
        repository.save(parking);

        return parking;
    }

    @Transactional
    public Parking checkout(String license) {
        Parking parking = findByLicense(license);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        repository.save(parking);

        return parking;
    }
}
