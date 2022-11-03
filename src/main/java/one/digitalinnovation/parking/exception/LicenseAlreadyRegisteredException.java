package one.digitalinnovation.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LicenseAlreadyRegisteredException extends RuntimeException {

    public LicenseAlreadyRegisteredException(String license) {
        super("Esta placa: " + license + " Já está cadastrada no sistema");
    }
}
