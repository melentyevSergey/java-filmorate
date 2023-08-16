package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Mpa {

    /** Поле с уникальным идентификатором MPA */
    private int id;

    /** Поле с названием MPA */
    @NotBlank(message = "Пустое название не допустимо.")
    private String name;
}