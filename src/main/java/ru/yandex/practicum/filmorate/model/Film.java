package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotBlank
    private String name;

    /** Поле с описанием фильма */
    @Size(min = 1, max = 200)
    private String description;

    /** Поле с датой релиза фильма */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    /** Поле с продолжительностью фильма */
    @Min(1)
    private long duration;
}
