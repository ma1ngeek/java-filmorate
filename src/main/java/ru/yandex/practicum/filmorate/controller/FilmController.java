package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("films")
@Slf4j
public class FilmController {

    @Autowired
    private FilmService service;

    @GetMapping
    public List<Film> getFilms() {
        log.info("GET /films/");
        return service.getAll();
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid @NotNull Film film) {
        log.info("POST /films/ " + film);
        validate(film);
        return service.insert(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid @NotNull Film film) {
        log.info("PUT /films/ " + film);
        validate(film);
        return service.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        log.info("GET /films/{}", id);
        return service.getById(id);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("PUT /films/{}/like/{}", filmId, userId);
        service.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void remLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("DELETE /films/{}/like/{}", filmId, userId);
        service.remLike(filmId, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") int count) {
        log.info("GET /films/popular?count={}", count);
        return service.top(count);
    }

    private final LocalDate minDate = LocalDate.of(1895, 12, 28);

    public void validate(@Valid Film film) {
        if (film.getReleaseDate().isBefore(minDate)) {
            log.error("некорректная дата: {}", film.getReleaseDate());
            throw new ValidationException("release date не должна быть ранее 28-12-1895");
        }
    }
}
