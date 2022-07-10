package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.catsgram.exceptions.*;
import ru.yandex.practicum.catsgram.model.FeedParams;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({UserNotFoundException.class, PostNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FeedParams.ErrorResponse notFoundHandler(final RuntimeException e) {
        return new FeedParams.ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public FeedParams.ErrorResponse conflictHandler(final UserAlreadyExistException e) {
        return new FeedParams.ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FeedParams.ErrorResponse invalidEmailHandler(final  InvalidEmailException e) {
        return new FeedParams.ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(IncorrectParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FeedParams.ErrorResponse incorrectParameterHandler(final IncorrectParameterException e) {
        return new FeedParams.ErrorResponse("Ошибка с полем " + e.getParameter());
    }
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FeedParams.ErrorResponse internalServerErrorHandler(final Throwable e) {
        return new FeedParams.ErrorResponse("Произошла непредвиденная ошибка");
    }
}
