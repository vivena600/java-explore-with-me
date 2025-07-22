package ru.practicum.ewmservice.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.base.dto.event.LocationDto;
import ru.practicum.ewmservice.base.enums.EventStateActionAdmin;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class RequestUpdateEventDto {
    @Size(max = 2000, min = 20)
    private String annotation;

    private Long category;

    @Size(max = 7000, min = 20)
    private String description;

    @Size(max = 120, min = 3)
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private EventStateActionAdmin stateAction;
}
