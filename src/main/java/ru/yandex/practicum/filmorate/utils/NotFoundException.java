package ru.yandex.practicum.filmorate.utils;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}