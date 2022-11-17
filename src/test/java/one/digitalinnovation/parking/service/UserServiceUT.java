package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.CloudParkingApplication;
import one.digitalinnovation.parking.config.ContainersEnvironment;
import one.digitalinnovation.parking.controller.dto.TokenDTO;
import one.digitalinnovation.parking.controller.dto.UserLoginDTO;
import one.digitalinnovation.parking.exception.UserExistsException;
import one.digitalinnovation.parking.exception.WrongUsernameOrPasswordException;
import one.digitalinnovation.parking.model.User;
import one.digitalinnovation.parking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CloudParkingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserServiceUT extends ContainersEnvironment {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    private void addUser() {
        User user = new User();
        user.setId(1L);
        user.setName("filipe");
        user.setUsername("filipe");
        user.setPassword("$2a$10$2gCF432/q.Pv9pVZFHXBb.tudKvqZNu3xjskGgJPoAtCBT/zdO062");//Senha criptografada
        repository.save(user);
    }

    @Test
    void whenCreatingAUserItMustReturnTheUserWithTheEncryptedPassword() {
        User user = new User();
        user.setName("joao");
        user.setUsername("joao");
        user.setPassword("joao123");

        User userCreated = service.createUser(user);

        Assertions.assertNotNull(userCreated);
        Assertions.assertNotEquals("filipe123", userCreated.getPassword());
    }

    @Test
    void whenCreatingAUserWithANameAlreadyRegisteredItShouldReturnException() {
        User user = new User();
        user.setName("filipe");
        user.setUsername("filipe");
        user.setPassword("filipe123");

        Assertions.assertThrows(UserExistsException.class, () -> service.createUser(user));
    }

    @Test
    void whenTheUserLogsInHeMustReturnTheTokenWithTheIssuedDateAndTime() {
        UserLoginDTO user = new UserLoginDTO();
        user.setUsername("filipe");
        user.setPassword("filipe123");

        TokenDTO token = service.loginUser(user);
        boolean initWithBearer = token.getToken().startsWith("Bearer ");

        Assertions.assertNotNull(token);
        Assertions.assertTrue(initWithBearer);
    }

    @Test
    void whenTheUserLogsInWrongItShouldReturnException() {
        UserLoginDTO user = new UserLoginDTO();
        user.setUsername("joao");
        user.setPassword("joao2123");

        Assertions.assertThrows(WrongUsernameOrPasswordException.class, () -> service.loginUser(user));
    }
}
