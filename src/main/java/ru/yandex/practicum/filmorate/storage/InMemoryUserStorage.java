package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class InMemoryUserStorage implements UserStorage {

    /** Поле с последним свободным уникальным идентификатором */
    private int uid = 0;

    /** Поле с таблицей уникальный идентификатор и пользователь */
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public boolean isUserPresent(int id) {
        return users.containsKey(id);
    }

    @Override
    public List<User> getUsers() {
        log.debug("Текущее количество пользователей: {}", users.values().size());

        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }

        user.setId(++uid);
        users.put(uid, user);

        log.debug("Создан новый пользователь с идентификатором: {}", user.getId());

        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(uid, user);

        log.debug("Обновлен пользователь с идентификатором: {}", user.getId());

        return user;
    }

    @Override
    public void removeUser(int id) {
        users.remove(id);

        log.debug("Удален пользователь с идентификатором: {}", id);
    }

    @Override
    public void addFriend(User user, int friendId) {
        user.addFriend(friendId);

        log.debug("Добавлен друг с id {} у пользователя с id {}", user.getId(), friendId);
    }

    @Override
    public void removeFriend(User user, int removeId) {
        user.removeFriend(removeId);

        log.debug("Удален друг с id {} у пользователя с id {}", user.getId(), removeId);
    }

    @Override
    public List<Integer> getFriends(User user) {
        List<Integer> friends = new ArrayList<>(user.getFriends());

        log.debug("Будет возвращен список друзей из {} записей", friends.size());

        return friends;
    }

    public void resetUserStorage() {
        uid = 0;
        users.clear();
    }
}
