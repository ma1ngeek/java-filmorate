package ru.yandex.practicum.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    private FilmController controller = new FilmController();

    private void check(Film film) {
        if (validator.validate(film).size() > 0) {
            throw new ValidationException("error");
        }
        controller.validate(film);
    }

    @Test
    public void validate() {
        Film film = new Film(1, "name", "description", LocalDate.of(2020, 6, 1), 90);

        // all correct
        assertDoesNotThrow(() -> check(film));
        // empty name
        film.setName(null);
        assertThrows(ValidationException.class, () -> check(film));
        film.setName("");
        assertThrows(ValidationException.class, () -> check(film));
        film.setName("name");

        // long description
        film.setDescription(String.format("%0200d", 0));
        assertDoesNotThrow(() -> check(film));
        film.setDescription(String.format("%0201d", 0));
        assertThrows(ValidationException.class, () -> check(film));
        film.setDescription("description");

        // release date
        LocalDate date = LocalDate.of(1895, 12, 28);
        film.setReleaseDate(date);
        assertDoesNotThrow(() -> check(film));
        film.setReleaseDate(date.minusDays(1));
        assertThrows(ValidationException.class, () -> check(film));
        film.setReleaseDate(LocalDate.of(2020, 6, 1));

        //positive duration
        film.setDuration(1);
        assertDoesNotThrow(() -> check(film));
        film.setDuration(0);
        assertThrows(ValidationException.class, () -> check(film));
        film.setDuration(-1);
        assertThrows(ValidationException.class, () -> check(film));
        film.setDuration(90);

    }

}