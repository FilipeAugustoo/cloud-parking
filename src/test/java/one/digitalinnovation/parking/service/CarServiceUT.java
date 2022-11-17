package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.CloudParkingApplication;
import one.digitalinnovation.parking.config.ContainersEnvironment;
import one.digitalinnovation.parking.exception.CarNotFoundException;
import one.digitalinnovation.parking.exception.LicenseAlreadyRegisteredException;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CloudParkingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CarServiceUT extends ContainersEnvironment {

    @Autowired
    private CarService service;

    @Autowired
    private CarRepository repository;

    @BeforeEach
    private void addCar() {
        Car car = new Car();
        car.setLicense("HDJ-2839");
        car.setModel("CORSA");
        car.setColor("RED");
        car.setState("SP");
        car.setIsParked(false);

        repository.save(car);
    }

    @Test
    void whenFindingAllCarsShouldReturnAList() {
        List<Car> cars = service.findAllCars();

        Assertions.assertNotNull(cars);
    }

    @Test
    void whenYouFindItByLicenseYouMustReturnTheCar() {
        Car car = service.findByLicense("HDJ-2839");

        Assertions.assertNotNull(car);
        Assertions.assertDoesNotThrow(() -> {
            service.findByLicense("HDJ-2839");
        });
    }

    @Test
    void whenFindingAWrongLicenseYouMustReturnAException() {

        Assertions.assertThrows(CarNotFoundException.class, () -> {
            service.findByLicense("MVH-8514");
        });
    }

    @Test
    void whenCreatingACarItMustReturnTheCreatedCar() {
        Car car = new Car();
        car.setLicense("JDN-9382");
        car.setState("SP");
        car.setModel("PALIO");
        car.setColor("RED");
        Car carCreated = service.create(car);

        Assertions.assertNotNull(carCreated);
    }

    @Test
    void whenCreatingACarWithAPlateAlreadyRegisteredItMustReturnException() {
        Car car = new Car();
        car.setLicense("HDJ-2839");
        car.setState("SP");
        car.setModel("PALIO");
        car.setColor("RED");

        Assertions.assertThrows(LicenseAlreadyRegisteredException.class, () -> {
            service.create(car);
        });
    }
}
