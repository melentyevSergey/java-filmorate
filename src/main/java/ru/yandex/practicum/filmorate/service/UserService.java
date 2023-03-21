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

    public User getUserById(int id) {
        if (id > 0 && userStorage.isUserPresent(id)) {
            log.debug("Валидация запроса завершена успешно.");

            return userStorage.getUserById(id);
        } else {
            throw new IdValidationException("Не корректный идентификатор пользователя.");
        }
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

    public User addFriend(int id, int friendId) {
        if (userStorage.isUserPresent(id) && userStorage.isUserPresent(friendId)) {
            return userStorage.addFriend(id, friendId);
        } else {
            throw new IdValidationException("Нет пользователя с таким идентификатором");
        }
    }

    public User removeFriend(int id, int removeId) {
        if (userStorage.isUserPresent(id) && userStorage.isUserPresent(removeId)) {
            return userStorage.removeFriend(id, removeId);
        } else {
            throw new IdValidationException("Нет пользователя с таким идентификатором");
        }
    }

    public List<Integer> getFriends(int id) {
        if (userStorage.isUserPresent(id)) {
            return userStorage.getFriends(id);
        } else {
            throw new IdValidationException("Нет пользователя с таким идентификатором");
        }

    }

    public List<Integer> getCommonFriends(int id, int otherId) {
        if (userStorage.isUserPresent(id) && userStorage.isUserPresent(otherId)) {
            return userStorage.getCommonFriends(id, otherId);
        } else {
            throw new IdValidationException("Нет пользователя с таким идентификатором");
        }
    }
}
