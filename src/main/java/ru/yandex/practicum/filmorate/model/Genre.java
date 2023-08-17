package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Genre {

    /** Поле с уникальным идентификатором жанра */
    private int id;

    /** Поле с названием жанра */
    @NotBlank(message = "Пустое название не допустимо")
    private String name;
}