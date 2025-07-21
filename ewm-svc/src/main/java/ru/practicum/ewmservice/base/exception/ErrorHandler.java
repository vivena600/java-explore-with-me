package ru.practicum.ewmservice.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequest(final Exception ex) {
        log.error("Bad request: {}", ex.getMessage(), ex);
        return ApiError.builder()
                .errors(getErrorMessages(ex))
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .reason("Incorrectly made request.")
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflict(final Exception ex) {
        log.error("conflict: {}", ex.getMessage(), ex);
        return ApiError.builder()
                .errors(getErrorMessages(ex))
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .reason("Integrity constraint has been violated.")
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(final Exception ex) {
        log.error("notFound: {}", ex.getMessage(), ex);
        return ApiError.builder()
                .errors(getErrorMessages(ex))
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .reason("The required object was not found.")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUnknownError(Exception ex) {
        log.error("UNHANDLED EXCEPTION: {}", ex.getMessage(), ex);
        return ApiError.builder()
                .errors(getErrorMessages(ex))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .reason("Unexpected internal server error")
                .build();
    }


    private static List<String> getErrorMessages(Throwable throwable) {
        List<String> errorMessages = new ArrayList<>();

        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
            errorMessages.add(throwable.getMessage());
        }

        return errorMessages;
    }
}
