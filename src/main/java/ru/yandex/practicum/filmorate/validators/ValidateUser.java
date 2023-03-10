package ru.yandex.practicum.filmorate.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.ValidationException;

import java.time.LocalDate;

public class ValidateUser {

    /** Поле со статическим экземпляром логгера */
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private ValidateUser() {}

    public static void validate(User user) {
        log.info("Запущен процесс валидации полученного пользователя.");

        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthday = user.getBirthday();
        LocalDate now = LocalDate.now();

        if (!email.contains("@")) {
            log.debug("Адрес электронной почты пустой или не содержит символа @");
            throw new ValidationException("Адрес электронной почты не может" +
                    " быть пустым или не содержать символа @");
        }
        if (login.contains(" ")) {
            log.debug("Логин пустой или содержит пробелы: {}", login);
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (birthday.isAfter(now)) {
            log.debug("День рождения указан будущей датой: {}", birthday);
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        log.info("Валидация полученного пользователя успешно завершена.");
    }
}
