package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.UserController;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import javax.validation.Valid;
import java.util.*;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final Map<String,User> users = new HashMap<>();

    public List<User> findAll() {
        log.debug("Количество пользователей: " + users.size());
        return new ArrayList<>(users.values());
    }

    public User findUserByEmail(String email) {
        if (!users.containsKey(email)) {
            throw new UserNotFoundException("Пользователь " + email + " не найден");
        }
        return users.get(email);
    }

    public User create(@Valid User user) throws UserAlreadyExistException {
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        }
        log.debug("Добавлен пользователь: " + user);
        users.put(user.getEmail(), user);
        return user;
    }

    public User updateUser(@Valid User user) throws UserNotFoundException {
        if (!users.containsKey(user.getEmail())) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        if (user.getEmail() == null) {
            throw new InvalidEmailException("Поле email не может быть пустым");
        }
        log.debug("Обновлены данные пользователя {}", user);
        users.put(user.getEmail(), user);
        return user;
    }
}
