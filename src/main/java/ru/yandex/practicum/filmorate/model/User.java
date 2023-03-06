package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;


/**
 * Класс пользователь
 * @author Мелентьев Сергей
 * @version 1.0
 */
@Data
@lombok.Builder(toBuilder = true)
public class User {

    /** Поле с идентификатором пользователя */
    private int id;

    /** Поле с электронным адресом пользователя */
    @Email
    private String email;

    /** Поле с логином пользователя */
    @NotNull
    private String login;

    /** Поле с именем пользователя */
    private String name;

    /** Поле с датой дня рождения пользователя */
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthday;

    public User(int id, @NonNull String email, @NonNull String login, String name,
                @PastOrPresent LocalDate birthday) {
        if ((name == null) || (name.isBlank())) {
            name = login;
        }
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
