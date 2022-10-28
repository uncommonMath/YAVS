package yavs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class CustomExHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Error> handeNotFoundException(Exception ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }


}
