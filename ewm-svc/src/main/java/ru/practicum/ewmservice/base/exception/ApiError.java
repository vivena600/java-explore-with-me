package ru.practicum.ewmservice.base.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}
