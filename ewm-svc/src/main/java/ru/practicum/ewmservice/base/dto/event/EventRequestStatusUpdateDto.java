package ru.practicum.ewmservice.base.dto.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.base.enums.StatusEventRequest;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class EventRequestStatusUpdateDto {
    private List<Long> requestIds;
    private StatusEventRequest status;
}
