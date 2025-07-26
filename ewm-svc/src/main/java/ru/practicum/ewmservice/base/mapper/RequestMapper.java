package ru.practicum.ewmservice.base.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmservice.base.dto.event.ParticipationRequestDto;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.model.Request;
import ru.practicum.ewmservice.base.model.User;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    default ParticipationRequestDto toDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .requester(request.getUser().getId())
                .event(request.getEvent().getId())
                .status(request.getState())
                .build();
    }

    default Request fromDto(ParticipationRequestDto dto, User user, Event event) {
        return Request.builder()
                .created(dto.getCreated())
                .state(dto.getStatus())
                .event(event)
                .user(user)
                .build();
    }
}
