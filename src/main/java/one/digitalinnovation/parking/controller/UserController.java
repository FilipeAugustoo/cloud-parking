package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.TokenDTO;
import one.digitalinnovation.parking.controller.dto.TokenValidateDTO;
import one.digitalinnovation.parking.controller.dto.UserCreateDTO;
import one.digitalinnovation.parking.controller.dto.UserLoginDTO;
import one.digitalinnovation.parking.controller.mapper.UserMapper;
import one.digitalinnovation.parking.model.User;
import one.digitalinnovation.parking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "User Controller")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/signin")
    @ApiOperation("Cadastra Usuário")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDTO userDto) {
        User user = mapper.toUser(userDto);
        User userCreated = service.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/login")
    @ApiOperation("Loga Usuário")
    public ResponseEntity<TokenDTO> loginUser(@RequestBody UserLoginDTO userDto) {
        TokenDTO token = service.loginUser(userDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/validate-token/{token}")
    @ApiOperation("Valida Token")
    public ResponseEntity<TokenValidateDTO> validateToken(@PathVariable String token) {
        TokenValidateDTO tokenValid = service.validateToken(token);
        if (tokenValid != null) {
            return ResponseEntity.ok(tokenValid);
        } else {
            return ResponseEntity.notFound().build();
        }


    }
}
