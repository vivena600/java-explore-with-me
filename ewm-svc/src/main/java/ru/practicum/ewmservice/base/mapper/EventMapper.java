    package ru.practicum.ewmservice.base.mapper;

    import org.mapstruct.Mapper;
    import ru.practicum.ewmservice.base.dto.category.CategoryDto;
    import ru.practicum.ewmservice.base.dto.event.*;
    import ru.practicum.ewmservice.base.dto.user.UserShortDto;
    import ru.practicum.ewmservice.base.enums.EventState;
    import ru.practicum.ewmservice.base.model.Category;
    import ru.practicum.ewmservice.base.model.Event;

    import java.time.LocalDateTime;

    @Mapper(componentModel = "spring")
    public interface EventMapper {

        default Event mapAddEventDto(AddEventDto addEventDto, Category category) {
            return Event.builder()
                    .annotation(addEventDto.getAnnotation())
                    .categoryId(category)
                    .createdOn(LocalDateTime.now())
                    .description(addEventDto.getDescription())
                    .title(addEventDto.getTitle())
                    .lon(addEventDto.getLocation().getLon())
                    .lat(addEventDto.getLocation().getLat())
                    .paid(addEventDto.getPaid())
                    .date(addEventDto.getEventDate())
                    .state(EventState.PENDING)
                    .requestModeration(addEventDto.getRequestModeration())
                    .participantLimit(addEventDto.getParticipantLimit())
                    .build();
        }

        default FullEventDto toFullEventDto(Event event, UserShortDto user, CategoryDto category) {
            return FullEventDto.builder()
                    .id(event.getId())
                    .annotation(event.getAnnotation())
                    .description(event.getDescription())
                    .title(event.getTitle())
                    .eventDate(event.getDate())
                    .confirmedRequests(event.getConfirmedRequests())
                    .location(LocationDto.builder().lat(event.getLat()).lon(event.getLon()).build())
                    .views(event.getViews())
                    .publishedOn(event.getPublishedOn())
                    .initiator(user)
                    .category(category)
                    .paid(event.getPaid())
                    .participantLimit(event.getParticipantLimit())
                    .requestModeration(event.getRequestModeration())
                    .state(event.getState())
                    .createdOn(event.getCreatedOn())
                    .publishedOn(event.getPublishedOn())
                    .build();
        }

        default ShortEventDto toShortEventDto(Event event, UserShortDto user, CategoryDto category) {
            return ShortEventDto.builder()
                    .id(event.getId())
                    .title(event.getTitle())
                    .eventDate(event.getDate())
                    .paid(event.getPaid())
                    .initiator(user)
                    .category(category)
                    .annotation(event.getAnnotation())
                    .build();
        }
    }
