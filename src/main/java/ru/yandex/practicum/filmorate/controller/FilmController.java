package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("films")
@Slf4j
public class FilmController {

    private static long counter = 1;
    private TreeMap<Long, Film> films = new TreeMap<>();

    @GetMapping
    public List<Film> getFilms() {
        return List.copyOf(films.values());
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid @NotNull Film film) {
        validate(film);
        film.setId(counter++);
        films.put(film.getId(), film);
        log.info("film created: {}", film);

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid @NotNull Film film) {
        validate(film);
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм #" + film.getId() + " не найден");
        }
        films.put(film.getId(), film);
        log.info("film updated: {}", film);

        if (counter <= film.getId()) {
            counter = film.getId() + 1;
        }
        return film;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Фильм #" + id + " не найден");
        }
        return films.get(id);
    }

    private final LocalDate minDate = LocalDate.of(1895, 12, 28);

    public void validate(@Valid Film film) {
        if (film.getReleaseDate().isBefore(minDate)) {
            log.error("некорректная дата: {}", film.getReleaseDate());
            throw new ValidationException("release date не должна быть ранее 28-12-1895");
        }
    }
}
