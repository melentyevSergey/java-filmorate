package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    /**
     * Метод для получения всех пользователей
     * @return - список всех пользователей
     */
    List<User> getUsers();

    /**
     * Метод для создания нового пользователя
     * @param user - сущность нового пользователя
     * @return сущность нового пользователя с уникальным идентификатором
     */
    User createUser(User user);

    /**
     * Метод для обновления пользователя
     * @param user - сущность пользователя
     * @return сущность измененного пользователя
     */
    User updateUser(User user);

    /**
     * Метод для удаления пользователя
     * @param id - уникальный идентификатор удаляемого пользователя
     */
    void removeUser(Integer id);

    boolean isUserPresent(Integer receivedUserId);

    void addFriend(Integer id, Integer friendId);

    void removeFriend(Integer id, Integer removeId);

    List<User> getFriends(Integer id);

    User getUserById(Integer id);

    List<User> getCommonFriends(Integer id, Integer otherId);
}
