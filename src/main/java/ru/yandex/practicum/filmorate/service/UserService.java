package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.IdValidationException;
import ru.yandex.practicum.filmorate.validators.ValidateUser;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        ValidateUser.validate(user);

        log.debug("Валидация пользователя при создании завершена успешно.");

        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        int receivedUserId = user.getId();
        if (userStorage.isUserPresent(receivedUserId)) {
            ValidateUser.validate(user);

            log.debug("Валидация пользователя при обновлении завершена успешно.");

            return userStorage.updateUser(user);
        } else {
            throw new IdValidationException("Нет пользователя с таким идентификатором.");
        }
    }

    public void addFriend(User user, int friendId) {
        userStorage.addFriend(user, friendId);
    }

    public void removeFriend(User user, int removeId) {
        userStorage.removeFriend(user, removeId);
    }

    public List<Integer> getFriends(User user) {
        return userStorage.getFriends(user);
    }
}
