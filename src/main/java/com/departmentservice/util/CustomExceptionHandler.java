package com.departmentservice.util;

import com.departmentservice.exception.NoSuchElementInDBException;
import com.departmentservice.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchElementInDBException.class)
    protected ResponseEntity<NoSuchElementInDBException> handleNoSuchElementInDBException(NoSuchElementInDBException ex) {
        return new ResponseEntity<>(new NoSuchElementInDBException(ex.getLocalizedMessage())
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ValidationException> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(new ValidationException(ex.getLocalizedMessage())
                , HttpStatus.NOT_ACCEPTABLE);
    }
}
