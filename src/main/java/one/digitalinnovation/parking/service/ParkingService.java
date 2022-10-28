package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private static final Map<String, Parking> parkingMap = new HashMap<>();

    static {
        var id = getUUID();
        var id2 = getUUID();
        Parking parking = new Parking(id, "HFJ-8358", "RJ", "CORSA", "AMARELO");
        Parking parking2 = new Parking(id2, "GDK-5385", "SP", "VECTRA", "CINZA");
        parkingMap.put(id, parking);
        parkingMap.put(id2, parking2);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public List<Parking> findAll() {
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    public Parking findById(String id) {
        Parking parking = parkingMap.get(id);

        if (parking == null) throw new ParkingNotFoundException(id);

        return parking;
    }

    public Parking create(Parking parking) {
        String uuid = getUUID();
        parking.setId(uuid);
        parking.setEntryDate(LocalDateTime.now());
        parkingMap.put(uuid, parking);
        return parking;
    }
}
