package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mappers.UserMapper;

import java.util.List;

@Slf4j
@Primary
@Repository
@AllArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userMapper);
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void removeUser(Integer id) {

    }

    @Override
    public boolean isUserPresent(Integer receivedUserId) {
        return false;
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {

    }

    @Override
    public void removeFriend(Integer id, Integer removeId) {

    }

    @Override
    public List<User> getFriends(Integer id) {
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        return null;
    }
}
