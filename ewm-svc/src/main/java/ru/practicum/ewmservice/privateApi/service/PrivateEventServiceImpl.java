package ru.practicum.ewmservice.privateApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.base.dto.category.CategoryDto;
import ru.practicum.ewmservice.base.dto.event.AddEventDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.user.UserShortDto;
import ru.practicum.ewmservice.base.exception.ConflictException;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.EventMapper;
import ru.practicum.ewmservice.base.model.Category;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.CategoryRepository;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final EventMapper eventMapper;

    private final UserMapper userMapper;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public FullEventDto createEvent(Long userId, AddEventDto dto) {
        log.info("Отправка запроса на добавление события {}", dto.toString());
        checkEventTime(dto.getEventDate());
        User user = checkUserById(userId);
        Category category = checkCategoryById(dto.getCategory());
        Event event = eventMapper.mapAddEventDto(dto, category);
        event.setCategoryId(category);
        event.setPublishedOn(LocalDateTime.now());
        event.setUserId(user);
        event.setViews(0L);
        UserShortDto userShortDto = userMapper.mapUserShortDto(user);
        CategoryDto categoryDto = categoryMapper.mapCategory(category);
        return eventMapper.toFullEventDto(eventRepository.save(event), userShortDto, categoryDto);
    }

    private Category checkCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id= " + id + " was not found"));
    }

    private User checkUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id= " + id + " was not found"));
    }

    private void checkEventTime(LocalDateTime event) {
        if (event == null && event.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Field: eventDate. Error: the date and time for which the event is scheduled " +
                    "cannot be earlier than two hours from the current moment. Value: " + event);
        }
    }
}
