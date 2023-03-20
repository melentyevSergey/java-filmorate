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
    void removeUser(int id);

    boolean isUserPresent(int receivedUserId);

    void addFriend(User user, int friendId);

    void removeFriend(User user, int removeId);

    List<Integer> getFriends(User user);
}
