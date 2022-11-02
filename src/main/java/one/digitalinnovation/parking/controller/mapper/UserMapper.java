package one.digitalinnovation.parking.controller.mapper;

import one.digitalinnovation.parking.controller.dto.UserCreateDTO;
import one.digitalinnovation.parking.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public User toUser(UserCreateDTO userDto) {
        return MODEL_MAPPER.map(userDto, User.class);
    }
}
