package one.digitalinnovation.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class WrongUsernameOrPasswordException extends RuntimeException {

    public WrongUsernameOrPasswordException(String msg) {
        super(msg);
    }
}
