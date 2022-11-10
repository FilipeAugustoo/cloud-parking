package one.digitalinnovation.parking.controller.dto;

import lombok.Getter;
import lombok.Setter;
import one.digitalinnovation.parking.model.Car;

import java.util.List;

@Getter
@Setter
public class ParkingDTO {

    private String name;
    private List<Car> cars;
    private int VAGAS_OCUPADAS;
    private int QUANT_VAGAS;
}
