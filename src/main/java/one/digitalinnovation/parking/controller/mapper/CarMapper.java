package one.digitalinnovation.parking.controller.mapper;

import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.model.Car;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public Car toCar(CarCreateDTO dto) {
        return MODEL_MAPPER.map(dto, Car.class);
    }
}
