package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdUnknownException;
import ru.yandex.practicum.filmorate.utils.ValidationException;
import ru.yandex.practicum.filmorate.validators.ValidateUser;

import java.util.*;


@RestController
public class UserController {

    /** Поле со статическим экземпляром логгера */
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    /** Поле с таблицей уникальный идентификатор и пользователь */
    private final Map<Integer, User> users = new HashMap<>();

    /** Поле с последним свободным уникальным идентификатором */
    private int uid = 0;

    @GetMapping("/users")
    public List<User> getUsers() {
        // Получение списка всех пользователей

        log.info("Получен запрос GET на получение списка всех пользователей.");
        log.debug("Текущее количество пользователей: {}", users.values().size());
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        // Создание нового пользователя
        log.info("Получен запрос POST для создания нового пользователя.");

        ValidateUser.validate(user);

        if (users.containsKey(user.getId())) {
            log.debug("Пользователь с таким идентификатором уже существует.");
            throw new ValidationException("Пользователь с идентификатором " + user.getId() +
                    " уже существует.");
        }

        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }

        user.setId(++uid);
        users.put(uid, user);

        log.debug("Создан новый пользователь: {}", user);
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        // Обновление существующего пользователя
        log.info("Получен запрос PUT для обновления существующего пользователя.");

        int receivedUserId = user.getId();
        if (users.containsKey(receivedUserId)) {
            ValidateUser.validate(user);
            users.put(uid, user);
        } else {
            throw new IdUnknownException("Нет пользователя с таким идентификатором.");
        }

        log.debug("Обновлен пользователь: {}", user);
        return user;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String validationException(ValidationException exception) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(exception.getMessage());
    }

    public void clearUsers() {
        uid = 0;
        users.clear();
    }
}
