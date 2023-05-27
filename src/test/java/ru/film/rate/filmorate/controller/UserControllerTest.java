package ru.film.rate.filmorate.controller;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.film.rate.filmorate.exception.ValidationException;
import ru.film.rate.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    private UserController controller = new UserController();

    private void check(User user) {
        if (validator.validate(user).size() > 0) {
            throw new ValidationException("error");
        }
        controller.validate(user);
    }

    @Test
    public void validate() {
        LocalDate birthday = LocalDate.of(2000, 6, 1);
        User user = new User(1, "login@gmail.com", "login", "name", birthday);

        // all correct
        assertDoesNotThrow(() -> check(user));

        // e-mail
        user.setEmail(null);
        assertThrows(ValidationException.class, () -> check(user));
        user.setEmail("");
        assertThrows(ValidationException.class, () -> check(user));
        user.setEmail("login_gmail.com");
        assertThrows(ValidationException.class, () -> check(user));
        user.setEmail("login@gmail.com");

        // login not empty
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> check(user));
        user.setLogin("");
        assertThrows(ValidationException.class, () -> check(user));
        user.setLogin("log in");
        assertThrows(ValidationException.class, () -> check(user));
        user.setLogin("login");

        // empty name
        user.setName(null);
        check(user);
        assertEquals(user.getLogin(), user.getName());

        // future birthday
        LocalDate date = LocalDate.now().plusDays(1);
        user.setBirthday(date);
        assertThrows(ValidationException.class, () -> check(user));
        user.setBirthday(birthday);
    }
}