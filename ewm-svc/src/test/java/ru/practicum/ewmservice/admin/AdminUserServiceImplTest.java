package ru.practicum.ewmservice.admin;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.admin.service.AdminUserServiceImpl;
import ru.practicum.ewmservice.base.dto.AddUserDto;
import ru.practicum.ewmservice.base.dto.UserDto;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class AdminUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper;

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    private final User user = User.builder()
            .email("email1")
            .name("name")
            .id(1L)
            .build();

    private final UserDto userDto = UserDto.builder()
            .email("email1")
            .name("name")
            .id(1L)
            .build();

    private final AddUserDto addUserDto = AddUserDto.builder()
            .email("email1")
            .name("name")
            .build();

    private static void checkUser(UserDto result, UserDto user) {
        assertNotNull(result);
        assertEquals(result.getId(), user.getId());
        assertEquals(result.getEmail(), user.getEmail());
        assertEquals(result.getName(), user.getName());
    }

    @Test
    public void createUserSuccess() {
        when(userMapper.mapAddUser(addUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapUser(user)).thenReturn(userDto);

        UserDto result = adminUserService.createUser(addUserDto);
        checkUser(result, userDto);

        verify(userMapper, times(1)).mapAddUser(addUserDto);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).mapUser(user);
    }

    @Nested
    class getUser {

        @Test
        public void getUserByIdSuccess() {
            Long id = 1L;

            when(userRepository.findAllById(List.of(id))).thenReturn(List.of(user));
            when(userMapper.mapUser(user)).thenReturn(userDto);

            List<UserDto> result = adminUserService.getUsers(List.of(id), 0, 0);

            assertNotNull(result);
            checkUser(result.get(0), userDto);

            verify(userRepository, times(1)).findAllById(List.of(id));
            verify(userMapper, times(1)).mapUser(user);
        }

        @Test
        public void getUserByIdFail() {
            Long failId = 999L;

            when(userRepository.findAllById(List.of(failId))).thenReturn(new ArrayList<>());

            List<UserDto> result = adminUserService.getUsers(List.of(failId), 0, 0);
            assertEquals(result.size(), 0);

            verify(userRepository, times(1)).findAllById(List.of(failId));
        }

        /*
        @Test
        public void getUsersFindAll() {
            when(userRepository.findAll(PageRequest.of(0, 1)))
                    .thenReturn(new PageImpl<>(List.of(user)));

            List<UserDto> result = adminUserService.getUsers(null, 0, 1);

            assertNotNull(result);
            checkUser(result.get(0), userDto);
m
            verify(userRepository, times(1)).findAll();
            verify(userMapper, times(1)).mapUser(user);
        }

         */
    }
}
