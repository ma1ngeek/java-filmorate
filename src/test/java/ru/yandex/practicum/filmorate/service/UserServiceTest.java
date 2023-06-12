package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    private User createUser() {
        return User.builder()
                .id(-1)
                .email("login@gmail.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2000, 6, 1))
                .build();
    }

    @Test
    void getAll() {
        User user = createUser();

        user = service.insert(user);
        List<User> list = service.getAll();

        assertTrue(list.contains(user));
    }

    @Test
    void getById() {
        User user = createUser();
        user = service.insert(user);
        User user2 = service.getById(user.getId());

        assertEquals(user, user2);
    }

    @Test
    void insert() {
        User user = createUser();
        user = service.insert(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    void update() {
        User user = createUser();
        user = service.insert(user);
        user.setEmail("newmail@yandex.com");
        service.update(user);
        User userUpd = service.getById(user.getId());
        assertEquals(user.getEmail(), userUpd.getEmail());
    }

    @Test
    void friends() {
        User user1 = createUser();
        user1 = service.insert(user1);
        User user2 = createUser();
        user2 = service.insert(user2);
        User user3 = createUser();
        user3 = service.insert(user3);

        service.addFriend(user1.getId(), user3.getId());
        service.addFriend(user2.getId(), user3.getId());

        user1 = service.getById(user1.getId());
        user2 = service.getById(user2.getId());
        user3 = service.getById(user3.getId());

        assertTrue(user1.getFriends().contains(user3.getId()));
        assertTrue(user2.getFriends().contains(user3.getId()));

        List<User> list = service.commonFriends(user1.getId(), user2.getId());
        assertEquals(1, list.size());
        assertEquals(user3.getId(), list.get(0).getId());
    }
}