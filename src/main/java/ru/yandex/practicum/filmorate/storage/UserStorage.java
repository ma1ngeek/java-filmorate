package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getAll();

    User getById(long id);

    User insert(User user);

    User update(User user);

    User delete(User user);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);
}
