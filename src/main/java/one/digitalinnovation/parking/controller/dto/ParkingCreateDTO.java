package one.digitalinnovation.parking.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ParkingCreateDTO {

    @Pattern(regexp = "^[a-zA-Z]{3}\\-\\d{4}$", message = "Placa deve ser no formato: '000-AAAA'")
    @NotBlank
    private String license;

    @NotBlank
    @Size(max = 2, message = "Estado deve ser no formato: 'AA'")
    private String state;

    @NotBlank
    private String model;

    @NotBlank
    private String color;
}
