package one.digitalinnovation.parking.controller;

import one.digitalinnovation.parking.CloudParkingApplication;
import one.digitalinnovation.parking.config.ContainersEnvironment;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.CarRepository;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CloudParkingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ParkingControllerIT extends ContainersEnvironment {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ParkingRepository repository;
    @Autowired
    private CarRepository carRepository;

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmaWxpcGUiLCJpc3MiOiJBUEkgVm9sbC5tZWQiLCJleHAiOjE2Njg2MzgwMDh9.5jdgjtyjaIfHqOi1ciHgGIgLj0h2dv-QrCJqhQJ236w";

    @BeforeEach
    public void addParking() {
        var parking = new Parking();
        var car = new Car();
        car.setIsParked(true);
        car.setState("DF");
        car.setColor("RED");
        car.setLicense("NFH-3820");
        car.setModel("CORSA");
        var carParked = new Car();
        carParked.setIsParked(true);
        carParked.setState("DF");
        carParked.setEntryDate(LocalDateTime.now());
        carParked.setColor("RED");
        carParked.setLicense("LWS-7483");
        carParked.setModel("CORSA");
        parking.setId("f231373a27584403bf89c6150aff5287");
        parking.setName("CLOUD_PARKING");
        parking.setNUMBER_VACANCIES(5);
        parking.setOCCUPIED_VACANCIES(0);
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(carParked);
        parking.setCars(cars);
        carRepository.save(carParked);
        carRepository.save(car);
        repository.save(parking);
    }

    @Test
    void whenFindAllThenCheckResult() throws Exception {
        mockMvc
                .perform(get("/parking")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void whenEnteringACarWithWrongLicensePlateOrNotRegisteredReturnNotFound() throws Exception {
        mockMvc
                .perform(post("/parking/entry/HSJ-7362")
                        .header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenEnteringACarItMustReturnOkWithTheDateAndTimeOfEntry() throws Exception {
        mockMvc
                .perform(post("/parking/entry/NFH-3820")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entryDate").isNotEmpty())
                .andExpect(jsonPath("$.isParked").value(true));
    }

    @Test
    void whenLeavingACarYouMustAddTheDateAndTimeOfDeparture() throws Exception {
        mockMvc
                .perform(post("/parking/exit/LWS-7483")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exitDate").isNotEmpty())
                .andExpect(jsonPath("$.isParked").value(false))
                .andExpect(jsonPath("$.bill").isNotEmpty());
    }

}