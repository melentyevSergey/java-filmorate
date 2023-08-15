package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mappers.UserMapper;
import ru.yandex.practicum.filmorate.utils.UserNotFoundException;

import java.sql.Date;
import java.sql.PreparedStatement;
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
    public User getUserById(Integer id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, userMapper, id);
    }

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users (name, email, login, birthday) values(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getLogin());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            user.setId((Integer) keyHolder.getKey());
        }

        log.debug("Создание нового пользователя завершено успешно.");
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET " +
                "name = ?, email = ?, login = ?, birthday = ? " +
                "WHERE user_id = ?";

        jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                Date.valueOf(user.getBirthday()),
                user.getId());

        log.debug("Обновление пользователя завершено успешно.");
        return user;
    }

    @Override
    public void removeUser(Integer id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isUserPresent(Integer id) {
        String sql = "SELECT COUNT (*) FROM users WHERE user_id = ?";
        if (jdbcTemplate.queryForObject(sql, Integer.class, id) == 0) {
            log.debug(String.format("Пользователь с идентификатором %s не найден", id));
            throw new UserNotFoundException(String.format("Пользователь с идентификатором %s не найден", id));
        }
        return true;
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        isUserPresent(id);
        isUserPresent(friendId);
        String sql = "INSERT INTO friends (friend_one_id, friend_two_id, status) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, friendId, false);
    }

    @Override
    public void removeFriend(Integer id, Integer removeId) {
        String sql = "DELETE FROM friends WHERE friend_one_id = ? AND friend_two_id = ?";
        jdbcTemplate.update(sql, id, removeId);
    }

    @Override
    public List<User> getFriends(Integer id) {
        String sql = "SELECT * FROM users WHERE user_id " +
                "IN (SELECT friend_two_id FROM friends WHERE friend_one_id = ?)";
        return jdbcTemplate.query(sql, userMapper, id);
    }

    @Override
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        String sql = "SELECT *" +
                "FROM users WHERE user_id IN (SELECT friend_two_id " +
                "FROM friends AS f1 WHERE friend_one_id = ? " +
                "AND friend_two_id IN " +
                "(SELECT friend_two_id FROM friends AS f2 WHERE friend_one_id = ?))";
        return jdbcTemplate.query(sql, userMapper, id, otherId);
    }
}
