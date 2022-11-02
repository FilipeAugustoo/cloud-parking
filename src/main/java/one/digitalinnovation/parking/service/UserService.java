package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.TokenDTO;
import one.digitalinnovation.parking.controller.dto.UserLoginDTO;
import one.digitalinnovation.parking.exception.UserExistsException;
import one.digitalinnovation.parking.model.User;
import one.digitalinnovation.parking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public User createUser(User user) {
        boolean usuarioExiste = repository.findByUsername(user.getUsername())
                .isPresent();

        if (usuarioExiste) throw new UserExistsException("Usuário já existe");

        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);

        return user;
    }

    public TokenDTO loginUser(UserLoginDTO userDto) {
        String uri = "http://localhost:8080/login";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(uri, userDto, String.class);
        var token = new TokenDTO();
        token.setHoraEmitida(LocalDateTime.now());
        token.setToken("Bearer " + response);

        return token;
    }
}
