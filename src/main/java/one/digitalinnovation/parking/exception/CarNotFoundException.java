package one.digitalinnovation.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String license) {
        super("O carro com essa placa: " + license + " não está cadastrado");
    }
}
