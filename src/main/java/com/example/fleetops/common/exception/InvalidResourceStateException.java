package com.example.fleetops.common.exception;

public class InvalidResourceStateException extends RuntimeException {

    public InvalidResourceStateException(String message) {
        super(message);
    }

    public InvalidResourceStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
