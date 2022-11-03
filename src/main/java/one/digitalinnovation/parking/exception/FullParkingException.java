package one.digitalinnovation.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FullParkingException extends RuntimeException {

    public FullParkingException(String msg) {
        super(msg);
    }
}
