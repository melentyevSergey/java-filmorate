package ru.yandex.practicum.filmorate.storage.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
@Component
public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("user_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String login= rs.getString("login");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        log.debug("Маппинг данных из БД на объект Пользователь завершен. ");
        return new User(id, name, email, login, birthday);
    }
}