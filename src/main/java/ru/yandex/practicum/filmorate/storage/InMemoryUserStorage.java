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
    public User getUserById(int id) {
        return users.get(id);
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
    public User addFriend(int id, int friendId) {
        User user = users.get(id);
        user.addFriend(friendId);

        users.put(id, user);

        log.debug("Пользователю {} добавлен друг {}", id, friendId);

        return user;
    }

    @Override
    public User removeFriend(int id, int removeId) {
        User user = users.get(id);
        user.removeFriend(removeId);

        users.put(id, user);

        log.debug("Удален друг {} у пользователя {}", removeId, id);

        return user;
    }

    @Override
    public List<Integer> getFriends(int id) {
        User user = users.get(id);
        List<Integer> friends = new ArrayList<>(user.getFriends());

        log.debug("Будет возвращен список друзей из {} записей", friends.size());

        return friends;
    }

    @Override
    public List<Integer> getCommonFriends(int id, int otherId) {
        User user = users.get(id);
        User otherUser = users.get(otherId);

        List<Integer> userFriends = new ArrayList<>(user.getFriends());
        List<Integer> common = new ArrayList<>(otherUser.getFriends());
        common.retainAll(userFriends);

        return common;
    }

    public void resetUserStorage() {
        uid = 0;
        users.clear();
    }
}
