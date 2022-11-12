package one.digitalinnovation.parking.exceptionhandler;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.exception.CarNotFoundException;
import one.digitalinnovation.parking.exception.FullParkingException;
import one.digitalinnovation.parking.exception.LicenseAlreadyRegisteredException;
import one.digitalinnovation.parking.exception.UserExistsException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(CarNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitle(ex.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(LicenseAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleLicenseAlreadyRegistered(LicenseAlreadyRegisteredException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitle(ex.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(FullParkingException.class)
    public ResponseEntity<Object> handleLicenseFullParking(FullParkingException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitle(ex.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExists(UserExistsException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitle(ex.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        var campos = new ArrayList<Problem.Field>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            campos.add(new Problem.Field(nome, mensagem));
        }

        var problema = new Problem();
        problema.setStatus(status.value());
        problema.setTitle("Um ou mais campos estão inválidos. " + "Faça o preenchimento correto e tente novamente");
        problema.setDateTime(OffsetDateTime.now());
        problema.setFields(campos);

        return handleExceptionInternal(ex, problema, headers, status, request);
    }
}
