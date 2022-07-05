package ru.yandex.practicum.catsgram.exceptions;

import javax.management.InstanceAlreadyExistsException;

public class UserAlreadyExistException extends InstanceAlreadyExistsException {

    public UserAlreadyExistException(String message) {
        super(message);
    }

}
