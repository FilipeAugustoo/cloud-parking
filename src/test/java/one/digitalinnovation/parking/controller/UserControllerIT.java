package one.digitalinnovation.parking.controller;

import one.digitalinnovation.parking.CloudParkingApplication;
import one.digitalinnovation.parking.config.ContainersEnvironment;
import one.digitalinnovation.parking.controller.dto.UserCreateDTO;
import one.digitalinnovation.parking.controller.dto.UserLoginDTO;
import one.digitalinnovation.parking.model.User;
import one.digitalinnovation.parking.repository.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CloudParkingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIT extends ContainersEnvironment {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void addUser() {
        User user = new User();
        user.setName("joao");
        user.setUsername("joao");
        user.setPassword("$2a$10$2gCF432/q.Pv9pVZFHXBb.tudKvqZNu3xjskGgJPoAtCBT/zdO062");
        repository.save(user);
    }

    @Test
    void whenCreatingAUserYouShouldReturnCreated() throws Exception {
        var user = new UserCreateDTO();
        user.setName("filipe");
        user.setPassword("filipe123");
        user.setUsername("filipe");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String body = writer.writeValueAsString(user);

        mockMvc
                .perform(post("/user/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("filipe"));
    }

    @Test
    void whenCreatingAUserWithUppercaseUsernameItShouldReturnBadRequest() throws Exception {
        var user = new UserCreateDTO();
        user.setName("filipe");
        user.setPassword("filipe123");
        user.setUsername("Filipe");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String body = writer.writeValueAsString(user);

        mockMvc
                .perform(post("/user/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatingAUserWithAnExistingUsernameItShouldReturnBadRequest() throws Exception {
        var user = new UserCreateDTO();
        user.setName("joao");
        user.setPassword("joao123");
        user.setUsername("joao");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String body = writer.writeValueAsString(user);

        mockMvc
                .perform(post("/user/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenLoggingInUserShouldReturnOkAndAToken() throws Exception {
        var user = new UserLoginDTO();
        user.setUsername("joao");
        user.setPassword("filipe123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String body = writer.writeValueAsString(user);

        mockMvc
                .perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.token").isNotEmpty());

    }

    @Test
    void whenLoggingInWithTheWrongUsernameOrPasswordItShouldReturnForbidden() throws Exception {
        var user = new UserLoginDTO();
        user.setUsername("joao3");
        user.setPassword("filipe123d");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String body = writer.writeValueAsString(user);

        mockMvc
                .perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value("Usuário ou senha incorreto"));

    }
}
