package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.CloudParkingApplication;
import one.digitalinnovation.parking.config.ContainersEnvironment;
import one.digitalinnovation.parking.exception.CarNotFoundException;
import one.digitalinnovation.parking.exception.FullParkingException;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.CarRepository;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CloudParkingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ParkingServiceUT extends ContainersEnvironment {

    @Autowired
    private ParkingService service;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    private void addParking() {
        Parking parking = new Parking();
        Car carParked = new Car();
        carParked.setLicense("NCH-1498");
        carParked.setModel("CORSA");
        carParked.setIsParked(true);
        carParked.setState("RJ");
        carParked.setEntryDate(LocalDateTime.now());
        carParked.setColor("BLUE");
        List<Car> cars = new ArrayList<>();
        cars.add(carParked);
        parking.setId("f231373a27584403bf89c6150aff5287");
        parking.setCars(cars);
        parking.setName("CLOUD_PARKING");
        parking.setOCCUPIED_VACANCIES(0);
        parking.setNUMBER_VACANCIES(5);
        carRepository.save(carParked);
        parkingRepository.save(parking);

        Car car = new Car();
        car.setLicense("GMF-5267");
        car.setIsParked(false);
        car.setState("DF");
        car.setModel("CAMARO");
        car.setColor("BLUE");

        carRepository.save(car);
    }

    @Test
    void whenYouLookForTheParkingLotMustReturnTheParking() {
        Parking parking = service.findParking();

        Assertions.assertNotNull(parking);
    }

    @Test
    void whenEnteringACarMustReturnTheCarWithTheDateAndTimeOfEntry() {
        Car car = service.entryCar("GMF-5267");
        Parking parking = service.findParking();

        Assertions.assertNotNull(car);
        Assertions.assertTrue(car.getIsParked());
        Assertions.assertNotNull(car.getEntryDate());
    }

    @Test
    void whenACarEntersAndTheParkingLotIsFullItMustReturnException() {
        Parking parking = service.findParking();
        parking.setOCCUPIED_VACANCIES(5);
        parkingRepository.save(parking);

        Assertions.assertThrows(FullParkingException.class, () -> service.entryCar("GMF-5267"));
    }

    @Test
    void whenACarEntersWithAnUnregisteredLicensePlateItMustReturnException() {

        Assertions.assertThrows(CarNotFoundException.class, () -> service.entryCar("MNV-2576"));
    }

    @Test
    void whenACarLeavesYouMustReturnTheCarWithTheDepartureDateAndTime() {
        Car car = carRepository.findByLicense("GMF-5267").get();
        car.setEntryDate(LocalDateTime.of(2022, 11, 17, 10, 0, 0));
        carRepository.save(car);

        Car carExited = service.exitCar("GMF-5267");


        Assertions.assertNotNull(carExited);
        Assertions.assertNotNull(carExited.getExitDate());
        Assertions.assertNotNull(carExited.getBill());
        Assertions.assertFalse(carExited.getIsParked());
    }

    @Test
    void whenACarToExitWithAnUnregisteredLicensePlateItShouldReturnAnException() {
        Assertions.assertThrows(CarNotFoundException.class, () -> service.exitCar("MNV-2576"));
    }
}
