package one.digitalinnovation.parking.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserCreateDTO {

    private String name;

    @Pattern(regexp = "^[a-z0-9_]+$", message = "Username deve ser em letras minúsculas")
    private String username;

    private String password;
}
