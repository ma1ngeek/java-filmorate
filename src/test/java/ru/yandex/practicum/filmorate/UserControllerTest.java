package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
        User user = User.builder()
                .id(1)
                .email("login@gmail.com")
                .login("login")
                .name("name")
                .birthday(birthday)
                .build();

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