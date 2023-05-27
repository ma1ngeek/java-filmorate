package ru.film.rate.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.film.rate.filmorate.exception.NotFoundException;
import ru.film.rate.filmorate.exception.ValidationException;
import ru.film.rate.filmorate.model.User;

import java.util.List;
import java.util.TreeMap;


@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    private static long counter = 1;
    private TreeMap<Long, User> users = new TreeMap<>();

    @GetMapping
    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody @Valid @NotNull User user) {
        validate(user);
        user.setId(counter++);
        users.put(user.getId(), user);
        log.info("user created: " + user);

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid @NotNull User user) {
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь #" + user.getId() + " не найден");
        }
        users.put(user.getId(), user);
        log.info("user updated: " + user);

        if (counter <= user.getId()) {
            counter = user.getId() + 1;
        }
        return user;
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
