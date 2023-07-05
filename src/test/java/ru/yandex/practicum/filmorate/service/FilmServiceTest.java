package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmServiceTest {

    @Autowired
    private FilmService service;
    @Autowired
    private UserService userService;

    private Film createFilm() {
        return Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2020, 6, 1))
                .duration(90)
                .build();
    }

    @Test
    void getAll() {
        Film film = createFilm();

        film = service.insert(film);

        List<Film> list = service.getAll();
        assertTrue(list.contains(film));

    }

    @Test
    void getById() {
        Film film = createFilm();
        film = service.insert(film);

        Film film2 = service.getById(film.getId());

        assertEquals(film, film2);
    }

    @Test
    void insert() {
        Film film = createFilm();

        film = service.insert(film);
        assertTrue(film.getId() > 0);
    }

    @Test
    void update() {
        Film film = createFilm();

        film = service.insert(film);
        film.setDescription("updated description");
        service.update(film);
        Film filmUpd = service.getById(film.getId());
        assertEquals(film.getDescription(), filmUpd.getDescription());
    }

    @Test
    void likes() {
        User user = User.builder()
                .login("login")
                .email("mail@email.com")
                .birthday(LocalDate.now())
                .build();
        userService.insert(user);

        Film film = createFilm();
        film = service.insert(film);
        service.addLike(film.getId(), user.getId());
        film = service.getById(film.getId());
        assertTrue(film.getLikes().contains(user.getId()));

        service.remLike(film.getId(), user.getId());
        film = service.getById(film.getId());
        assertFalse(film.getLikes().contains(user.getId()));
    }

}