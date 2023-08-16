package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Builder
public class User {

    /** Поле с уникальным идентификатором пользователя */
    private int id;

    /** Поле с адресом электронной почты */
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;

    /** Поле с логином пользователя */
    @NotBlank(message = "логин не может быть пустым и содержать пробелы")
    private String login;

    /** Поле с именем пользователя */
    private String name;

    /** Поле с датой рождения пользователя */
    @PastOrPresent(message = "дата рождения не может быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}