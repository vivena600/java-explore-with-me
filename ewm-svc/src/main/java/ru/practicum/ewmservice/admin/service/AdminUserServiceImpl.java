package ru.practicum.ewmservice.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.base.dto.AddUserDto;
import ru.practicum.ewmservice.base.dto.UserDto;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        if (ids == null || ids.isEmpty()) {
            log.info("get users from {} size {}", from, size);
            return userRepository.findAll(PageRequest.of(from, size))
                    .stream()
                    .map(userMapper::mapUser)
                    .toList();
        } else {
            log.info("getUsers ids: {}", ids);
            return userRepository.findAllById(ids)
                    .stream()
                    .map(userMapper::mapUser)
                    .toList();
        }
    }

    @Transactional
    @Override
    public UserDto createUser(AddUserDto userDto) {
        log.info("create user {}", userDto);
        User entity = userMapper.mapAddUser(userDto);
        return userMapper.mapUser(userRepository.save(entity));
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        log.info("delete user {} ", id);
        User entity = checkUser(id);
        userRepository.delete(entity);
    }

    private User checkUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " was not found") );
    }
}
