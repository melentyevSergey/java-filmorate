package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;


@Slf4j
@RestController
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET на получение списка всех пользователей.");

        return service.getUsers();
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        log.info("Получен запрос POST для создания нового пользователя.");

        return service.createUser(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        log.info("Получен запрос PUT для обновления существующего пользователя.");

        return service.updateUser(user);
    }

    @ResponseBody
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен запрос GET для получения пользователя по идентификатору {}", id);

        return service.getUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void updateUserWithFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос PUT для добавления пользователю {} друга {}", id, friendId);

        service.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriendFromUser(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос DELETE для удаления друга {} у пользователя {}", friendId, id);

        service.removeFriend(id, friendId);
    }

    @ResponseBody
    @GetMapping("/users/{id}/friends")
    public List<User> getFriendsByUserId(@PathVariable int id) {
        log.info("Получен запрос GET для получения друзей пользователя {}", id);

        return service.getFriends(id);
    }

    @ResponseBody
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getFriendsByUserId(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос GET для получения списка друзей пользователя {}, " +
                "общих с пользователем {}", id, otherId);

        return service.getCommonFriends(id, otherId);
    }
}
