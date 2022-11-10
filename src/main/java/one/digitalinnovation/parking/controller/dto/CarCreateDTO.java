package one.digitalinnovation.parking.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CarCreateDTO {


    @Pattern(regexp = "^[A-Z]{3}\\-\\d{4}$", message = "Placa deve ser no formato: 'AAA-0000'")
    @NotBlank(message = "License é obrigatório")
    private String license;

    @NotBlank(message = "State é obrigatório")
    @Size(max = 2, message = "State deve ser no formato: 'AA'")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Deve ser em letras maiúsculas")
    private String state;

    @NotBlank(message = "Model é obrigatório")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Deve ser em letras maiúsculas")
    private String model;

    @NotBlank(message = "Color é obrigatório")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Deve ser em letras maiúsculas")
    private String color;
}
