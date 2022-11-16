package one.digitalinnovation.parking.controller;

import one.digitalinnovation.parking.CloudParkingApplication;
import one.digitalinnovation.parking.config.ContainersEnvironment;
import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.model.Car;
import one.digitalinnovation.parking.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CloudParkingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CarControllerIT extends ContainersEnvironment {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository repository;

    @BeforeEach
    public void addCar() {
        var car = new Car();
        car.setLicense("HFN-8372");
        car.setModel("CORSA");
        car.setColor("RED");
        car.setState("DF");
        car.setIsParked(false);
        car.setAmountParked(1);
        repository.save(car);
    }

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmaWxpcGUiLCJleHAiOjE2Njg1NTAxNTR9.ayv6aAgrPgY193m3wEW0K29bOqqONaIiEX0k3k7CxlQVMoLlNsffurwJeI-AkyiPnRJ89sO2ZgcPchh92rttaw";

    @Test
    void whenFindAllThenCheckResult() throws Exception {
        mockMvc
                .perform(get("/car")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindByLicenseReturnsTheCarWithTheSameLicense() throws Exception {
        mockMvc
                .perform(get("/car/HFN-8372")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.license").value("HFN-8372"));
    }

    @Test
    void whenLocateByWrongLicenseItShouldReturnNotFound() throws Exception {
        mockMvc
                .perform(get("/car/HFN-8105")
                        .header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreatingACarItShouldReturnCreated() throws Exception {
        var car = new CarCreateDTO();
        car.setColor("BLUE");
        car.setLicense("HJD-2037");
        car.setModel("CORSA");
        car.setState("SP");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String body = writer.writeValueAsString(car);

        mockMvc
                .perform(post("/car")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.license").value("HJD-2037"));
    }

    @Test
    void whenCreatingACarWithOneOfTheWrongFieldsItShouldReturn() throws Exception {
        var car = new CarCreateDTO();
        car.setColor("red");
        car.setLicense("HJDd-20373");
        car.setModel("corsa");
        car.setState("df");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String body = writer.writeValueAsString(car);

        mockMvc
                .perform(post("/car")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
