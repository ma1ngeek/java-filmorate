package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class MpaDbStorageTest {

    @Autowired
    private MpaStorage storage;

    @Test
    void getAll() {
        List<Mpa> list = storage.findAll();
        assertNotNull(list);
        assertEquals(5, list.size());
    }

    @Test
    void getById() {
        Mpa mpa = storage.getById(3);
        assertNotNull(mpa);
        assertEquals("PG-13", mpa.getName());

        assertThrows(NotFoundException.class, () -> {
            storage.getById(100);
        });
    }
}