package ru.yandex.practicum.catsgram.exceptions;

public class PostNotFoundException extends UserNotFoundException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
