package ru.practicum.ewmservice.admin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.base.dto.user.AddUserDto;
import ru.practicum.ewmservice.base.dto.user.UserDto;
import ru.practicum.ewmservice.admin.service.AdminUserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<UserDto>> getUser(@RequestParam(required = false) List<Long> ids,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /admin/users");
        List<UserDto> result = userService.getUsers(ids, from, size);
        return ResponseEntity.ok(result);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid AddUserDto userDto) {
        log.info("POST /admin/users");
        UserDto result = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long userId) {
        log.info("DELETE /admin/users/{}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
