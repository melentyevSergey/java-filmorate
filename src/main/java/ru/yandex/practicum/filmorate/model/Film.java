package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * Класс фильм
 * @author Мелентьев Сергей
 * @version 1.0
 */
@lombok.Data
@lombok.Builder(toBuilder = true)
@lombok.AllArgsConstructor
@lombok.NonNull
public class Film {

    /** Поле с идентификатором фильма */
    private int id;

    /** Поле с наименованием фильма */
    private String name;

    /** Поле с описанием фильма */
    private String description;

    /** Поле с датой релиза фильма */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    /** Поле с продолжительностью фильма */
    private long duration;
}
