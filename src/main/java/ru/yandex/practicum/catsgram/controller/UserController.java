package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.List;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/users", "/users/{userEmail}"})
    public List<User> findAllUsers(@PathVariable(value = "userEmail", required = false) String email) {
        if (email == null) {
            return userService.findAll();
        } else {
            return List.of(userService.findUserByEmail(email));
        }
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) throws UserAlreadyExistException {

        return userService.create(user);
    }

    @PutMapping(value = "/users")
    public User putUser(@RequestBody User user) throws UserNotFoundException {
        return userService.updateUser(user);
    }

}
