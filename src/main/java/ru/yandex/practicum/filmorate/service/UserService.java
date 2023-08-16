package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.NotFoundException;
import ru.yandex.practicum.filmorate.validators.ValidateUser;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        ValidateUser.validate(user);

        return userStorage.createUser(user);
    }

    public User getUserById(int id) {
        if (id > 0 && userStorage.isUserPresent(id)) {
            log.debug("Валидация запроса завершена успешно.");

            return userStorage.getUserById(id);
        } else {
            throw new NotFoundException("Не корректный идентификатор пользователя.");
        }
    }

    public User updateUser(User user) {
        int receivedUserId = user.getId();
        if (userStorage.isUserPresent(receivedUserId)) {
            ValidateUser.validate(user);

            log.debug("Валидация пользователя при обновлении завершена успешно.");

            return userStorage.updateUser(user);
        } else {
            throw new NotFoundException("Нет пользователя с таким идентификатором.");
        }
    }

    public void addFriend(int id, int friendId) {
        if (userStorage.isUserPresent(id) && userStorage.isUserPresent(friendId)) {
            userStorage.addFriend(id, friendId);
        } else {
            throw new NotFoundException("Нет пользователя с таким идентификатором");
        }
    }

    public void removeFriend(int id, int removeId) {
        if (userStorage.isUserPresent(id) && userStorage.isUserPresent(removeId)) {
            userStorage.removeFriend(id, removeId);
        } else {
            throw new NotFoundException("Нет пользователя с таким идентификатором");
        }
    }

    public List<User> getFriends(int id) {
        if (userStorage.isUserPresent(id)) {
            return userStorage.getFriends(id);
        } else {
            throw new NotFoundException("Нет пользователя с таким идентификатором");
        }

    }

    public List<User> getCommonFriends(int id, int otherId) {
        if (userStorage.isUserPresent(id) && userStorage.isUserPresent(otherId)) {
            return userStorage.getCommonFriends(id, otherId);
        } else {
            throw new NotFoundException("Нет пользователя с таким идентификатором");
        }
    }
}
