package ru.practicum.ewmservice.base.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewmservice.base.enums.EventStateAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UpdateEventUserDto {
    @Length(max = 2000, min = 20)
    private String annotation;

    private Long categoryId;

    @Length(max = 7000, min = 20)
    private String description;

    @Length(max = 120, min = 3)
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    @Positive
    private Integer participantLimit;

    private Boolean requestModeration;

    private EventStateAction stateAction;
}
