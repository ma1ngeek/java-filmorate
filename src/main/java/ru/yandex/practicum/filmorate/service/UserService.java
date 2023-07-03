package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage storage;

    public List<User> getAll() {
        return storage.getAll();
    }

    public User getById(long id) {
        return storage.getById(id);
    }

    public User insert(@Valid User user) {
        log.info("user created: " + user);
        return storage.insert(user);
    }

    public User update(@Valid User user) {
        log.info("user updated: " + user);
        return storage.update(user);
    }

    public List<User> getFriends(long userId) {
        User user = getById(userId);
        return user.getFriends().stream().map(id -> getById(id)).collect(Collectors.toList());
    }

    public void addFriend(long userId, long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void remFriend(long userId, long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> commonFriends(long userId, long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        List<Long> list = new ArrayList<>(user.getFriends());
        Set<Long> frndLst = friend.getFriends();
        list.retainAll(frndLst);
        return list.stream().map(id -> getById(id)).collect(Collectors.toList());
    }

}
