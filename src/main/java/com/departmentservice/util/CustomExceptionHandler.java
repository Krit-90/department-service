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
    protected ResponseEntity<ErrorInfo> handleNoSuchElementInDBException(NoSuchElementInDBException ex) {
        return new ResponseEntity<>(new ErrorInfo(ex.getLocalizedMessage())
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorInfo> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(new ErrorInfo(ex.getLocalizedMessage())
                , HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorInfo> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>(new ErrorInfo(ex.getLocalizedMessage())
                , HttpStatus.BAD_REQUEST);
    }

    private class ErrorInfo {
        String description;

        public ErrorInfo() {
        }

        public ErrorInfo(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
