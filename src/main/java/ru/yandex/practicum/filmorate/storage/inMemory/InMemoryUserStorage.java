package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class InMemoryUserStorage implements UserStorage {

    /** Поле с последним свободным уникальным идентификатором */
    private int uid = 0;

    /** Поле с таблицей уникальный идентификатор и пользователь */
    private final Map<Integer, User> users = new HashMap<>();

    private final Map<Integer, Set<Integer>> friends = new HashMap<>();

    @Override
    public boolean isUserPresent(Integer id) {
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
    public User getUserById(Integer id) {
        return users.get(id);
    }

    @Override
    public User updateUser(User user) {
        users.put(uid, user);

        log.debug("Обновлен пользователь с идентификатором: {}", user.getId());

        return user;
    }

    @Override
    public void removeUser(Integer id) {
        users.remove(id);

        log.debug("Удален пользователь с идентификатором: {}", id);
    }

    @Override
    public void addFriend(Integer oneId, Integer twoId) {
        log.debug("Пользователи с идентификаторами {} и {} стали друзьями", oneId, twoId);
        friends.get(oneId).add(twoId);
        friends.get(twoId).add(oneId);
    }

    @Override
    public List<User> getFriends(Integer id) {
        log.debug("Текущее колличество друзей у пользователя с идентификатором {}: {}",
                id, friends.get(id).size());
        isUserPresent(id);
        return friends.get(id).stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        isUserPresent(id);
        isUserPresent(otherId);
        return friends.get(id).stream()
                .filter(friends.get(otherId)::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }

    public void resetUserStorage() {
        uid = 0;
        users.clear();
        friends.clear();
    }

    @Override
    public void removeFriend(Integer id, Integer removeId) {
        log.debug("Пользователи с идентификаторами {} и {} перестали дружить", id, removeId);
        friends.get(id).remove(removeId);
        friends.get(removeId).remove(id);
    }
}
