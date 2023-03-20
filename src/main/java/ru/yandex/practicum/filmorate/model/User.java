package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;


/**
 * Класс пользователь
 * @author Мелентьев Сергей
 * @version 1.0
 */
@Data
@Builder(toBuilder = true)
public class User {

    /** Поле с идентификатором пользователя */
    private int id;

    /** Поле с электронным адресом пользователя */
    @Email
    private String email;

    /** Поле с логином пользователя */
    @NotBlank
    private String login;

    /** Поле с именем пользователя */
    private String name;

    /** Поле с датой дня рождения пользователя */
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthday;

    /** Поле со списком друзей */
    private Set<Integer> friends;

    public User(int id, @NonNull String email, @NonNull String login, String name,
                @PastOrPresent LocalDate birthday, Set<Integer> friends) {
        if ((name == null) || (name.isBlank())) {
            name = login;
        }
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = friends;
    }

    public void addFriend(int id) {
        friends.add(id);
    }

    public void removeFriend(int id) {
        friends.remove(id);
    }
}
