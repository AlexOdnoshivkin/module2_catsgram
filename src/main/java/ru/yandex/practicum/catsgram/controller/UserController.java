package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashSet;
import java.util.Set;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final Set<User> users = new HashSet<>();

    @GetMapping("/users")
    public Set<User> findAllUsers() {
        log.debug("Количество пользователей: " + users.size());
        return users;
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) throws UserAlreadyExistException {
        if (user.getEmail() == null) {
            throw new InvalidEmailException("Поле email не может быть пустым");
        }
        if (users.contains(user)) {
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        }
        log.debug("Добавлен пользователь: " + user);
        users.add(user);
        return user;
    }

    @PutMapping(value = "/users")
    public User putUser(@RequestBody User user) {
        if (user.getEmail() == null) {
            throw new InvalidEmailException("Поле email не может быть пустым");
        }
        users.add(user);
        return user;
    }

}
