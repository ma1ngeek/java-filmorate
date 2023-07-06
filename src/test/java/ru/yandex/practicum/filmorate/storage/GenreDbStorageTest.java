package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class GenreDbStorageTest {

    @Autowired
    private GenreStorage storage;

    @Test
    void getAll() {
        List<Genre> list = storage.findAll();
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    @Test
    void getById() {
        Genre genre = storage.getById(3);
        assertNotNull(genre);
        assertEquals("Мультфильм", genre.getName());

        assertThrows(NotFoundException.class, () -> {
            storage.getById(100);
        });
    }
}