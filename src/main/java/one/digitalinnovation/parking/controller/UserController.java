package one.digitalinnovation.parking.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.TokenDTO;
import one.digitalinnovation.parking.controller.dto.UserCreateDTO;
import one.digitalinnovation.parking.controller.dto.UserLoginDTO;
import one.digitalinnovation.parking.controller.mapper.UserMapper;
import one.digitalinnovation.parking.model.User;
import one.digitalinnovation.parking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/signin")
    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO userDto) {
        User user = mapper.toUser(userDto);
        User userCreated = service.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginUser(@RequestBody UserLoginDTO userDto) {
        TokenDTO token = service.loginUser(userDto);
        return ResponseEntity.ok(token);
    }
}
