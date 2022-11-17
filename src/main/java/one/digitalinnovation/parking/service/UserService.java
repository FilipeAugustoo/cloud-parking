package one.digitalinnovation.parking.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.config.TokenService;
import one.digitalinnovation.parking.config.UserDetailsData;
import one.digitalinnovation.parking.controller.dto.TokenDTO;
import one.digitalinnovation.parking.controller.dto.UserLoginDTO;
import one.digitalinnovation.parking.exception.UserExistsException;
import one.digitalinnovation.parking.exception.WrongUsernameOrPasswordException;
import one.digitalinnovation.parking.model.User;
import one.digitalinnovation.parking.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    public User createUser(User user) {
        boolean usuarioExiste = repository.findByUsername(user.getUsername())
                .isPresent();

        if (usuarioExiste) throw new UserExistsException("Usuário já existe");

        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);

        return user;
    }

    public TokenDTO loginUser(UserLoginDTO userDto) {
        try {

            var authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
            TokenDTO tokenDTO = new TokenDTO();
            var tokenJWT = tokenService.gerarToken((UserDetailsData) authentication.getPrincipal());
            tokenDTO.setToken("Bearer " + tokenJWT);
            tokenDTO.setHoraEmitida(LocalDateTime.now());
            return tokenDTO;

        } catch (RuntimeException e) {
            throw new WrongUsernameOrPasswordException("Usuário ou senha incorreto");
        }


    }
}
