package one.digitalinnovation.parking.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String msg) {
        super(msg);
    }
}
