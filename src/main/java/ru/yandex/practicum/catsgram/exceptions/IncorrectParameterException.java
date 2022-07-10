package ru.yandex.practicum.catsgram.exceptions;

public class IncorrectParameterException extends RuntimeException{
    private final String parameter;

    public IncorrectParameterException(String parameter, String message) {
        super(message);
        this.parameter = parameter;
    }
}
