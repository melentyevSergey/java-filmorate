package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс пользователь
 * @author Мелентьев Сергей
 * @version 1.0
 */
@Data
@AllArgsConstructor
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
    private final Set<Integer> friends = new HashSet<>();

    /**
     * Метод для добавления друга
     * @param id - идентификатор пользователя
     */
    public void addFriend(int id) {
        friends.add(id);
    }

    /**
     * Метод для удаления друга
     * @param id - идентификатор пользователя
     */
    public void removeFriend(int id) {
        friends.remove(id);
    }
}
