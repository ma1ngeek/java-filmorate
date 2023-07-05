package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getUsers() {
        log.info("GET /users/");
        return service.getAll();
    }

    @PostMapping
    public User addUser(@RequestBody @Valid @NotNull User user) {
        log.info("POST /users/ {}", user);
        validate(user);
        return service.insert(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid @NotNull User user) {
        log.info("PUT /users/ {}", user);
        validate(user);
        return service.update(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        log.info("GET /users/{}", id);
        return service.getById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("PUT /users/{}/friends/{}", id, friendId);
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFromFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("DELETE /users/{}/friends/{}", id, friendId);
        service.remFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("GET /users/{}/friends", id);
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("GET /users/{}/friends/common/{}", id, friendId);
        return service.commonFriends(id, friendId);
    }

    public void validate(User user) {
        String login = user.getLogin();
        if (login.contains(" ")) {
            throw new ValidationException("Логин не должен содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(login);
        }
    }
}
