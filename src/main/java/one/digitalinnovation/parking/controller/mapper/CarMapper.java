package one.digitalinnovation.parking.controller.mapper;

import one.digitalinnovation.parking.controller.dto.CarCreateDTO;
import one.digitalinnovation.parking.controller.dto.CarDTO;
import one.digitalinnovation.parking.model.Car;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public Car toCar(CarCreateDTO dto) {
        return MODEL_MAPPER.map(dto, Car.class);
    }

    public CarDTO toCarDTO(Car car) {
        return MODEL_MAPPER.map(car, CarDTO.class);
    }

    public List<CarDTO> toCarDTOList(List<Car> carList) {
        return carList.stream()
                .map(this::toCarDTO)
                .collect(Collectors.toList());
    }
}
