package ru.practicum.ewmservice.base.exception;

public class ValidationRequestException extends RuntimeException {

    public ValidationRequestException(String message) {
        super(message);
    }
}
