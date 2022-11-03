package one.digitalinnovation.parking.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TokenDTO {

    private String token;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime horaEmitida;
}
