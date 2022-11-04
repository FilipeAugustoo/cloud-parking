package one.digitalinnovation.parking.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CarCreateDTO {


    @Pattern(regexp = "^[a-zA-Z]{3}\\-\\d{4}$", message = "Placa deve ser no formato: '000-AAAA'")
    @NotBlank(message = "License é obrigatório")
    private String license;

    @NotBlank(message = "State é obrigatório")
    private String state;

    @NotBlank(message = "Model é obrigatório")
    private String model;
    
    @NotBlank(message = "Color é obrigatório")
    private String color;
}
