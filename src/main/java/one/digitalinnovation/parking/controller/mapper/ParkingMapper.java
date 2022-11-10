package one.digitalinnovation.parking.controller.mapper;

import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ParkingMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDTO toParkingDTO(Parking parking) {
        return MODEL_MAPPER.map(parking, ParkingDTO.class);
    }
}
