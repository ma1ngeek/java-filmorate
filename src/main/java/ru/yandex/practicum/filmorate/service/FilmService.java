package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage storage;
    private final UserStorage userStorage;

    public List<Film> getAll() {
        return storage.getAll();
    }

    public Film getById(long id) {
        return storage.getById(id);
    }

    public Film insert(@Valid Film film) {
        log.info("film created: {}", film);
        return storage.insert(film);
    }

    public Film update(@Valid Film film) {
        log.info("film updated: {}", film);
        return storage.update(film);
    }

    public void addLike(long filmId, long userId) {
        Film film = getById(filmId);
        User user = userStorage.getById(userId);
        film.getLikes().add(userId);
    }

    public void remLike(long filmId, long userId) {
        Film film = getById(filmId);
        User user = userStorage.getById(userId);
        film.getLikes().remove(userId);
    }

    public List<Film> top(int count) {
        List<Film> list = getAll();
        return list.stream()
                .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
