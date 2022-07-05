package ru.yandex.practicum.catsgram.exceptions;

import java.security.InvalidParameterException;

public class InvalidEmailException extends InvalidParameterException {

    public InvalidEmailException (String message) {
        super(message);
    }
}
