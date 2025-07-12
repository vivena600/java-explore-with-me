package ru.practicum.excpection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String>  handleBadRequest(BadRequestException ex) {
        log.error("Bad request: {}", ex.getMessage());
        return Map.of("bad request", ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String>  handleBadRequest(InternalError ex) {
        log.error("InternalError: {}", ex.getMessage());
        return Map.of("bad request", ex.getMessage());
    }
}
