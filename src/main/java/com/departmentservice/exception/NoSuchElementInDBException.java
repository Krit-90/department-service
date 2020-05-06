package com.departmentservice.exception;

public class NoSuchElementInDBException extends RuntimeException {

    public NoSuchElementInDBException() {
        super();
    }

    public NoSuchElementInDBException(String message) {
        super(message);
    }
}
