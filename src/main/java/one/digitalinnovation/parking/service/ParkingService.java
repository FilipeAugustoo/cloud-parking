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
        parking.setEntryDate(LocalDateTime.of(2022, 10, 29, 8, 0, 0));
        Parking parking2 = new Parking(id2, "GDK-5385", "SP", "VECTRA", "CINZA");
        parking2.setEntryDate(LocalDateTime.of(2022, 10, 29, 17, 0, 0));
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

    public void delete(String id) {
        findById(id);
        parkingMap.remove(id);
    }

    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parkingMap.replace(id, parking);

        return parking;
    }

    public Parking exit(String id) {
        var parking = findById(id);
        parking.setExitDate(LocalDateTime.now());

        int horas = parking.getExitDate().getHour() - parking.getEntryDate().getHour();

        if (horas == 1) {
            parking.setBill(1.0);
        } else if (horas > 1 && horas <= 3) {
            parking.setBill(2.50);
        } else if (horas >= 4) {
            parking.setBill(5.00);
        } else {
            parking.setBill(0.0);
        }
        return parking;
    }
}
