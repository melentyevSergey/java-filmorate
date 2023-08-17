package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.utils.validator.ReleaseDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
@Builder
public class Film {

    /** Поле с уникальными жанрами */
    private final Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId));

    /** Поле с уникальным идентификатором фильма */
    private int id;

    /** Поле с наименованием фильма */
    @NotBlank(message = "Название не может быть пустым.")
    private String name;

    /** Поле с описанием фильма */
    @Size(min = 1, max = 200, message = "Максимальная длина описания — 200 символов.")
    private String description;

    /** Поле с датой релиза фильма */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ReleaseDateConstraint
    private LocalDate releaseDate;

    /** Поле с продолжительностью фильма */
    @Positive(message = "Продолжительность фильма должна быть положительной.")
    private int duration;

    /** Поле с рейтингом фильма */
    @NotNull
    private int rate;

    /** Поле со значением mpa фильма */
    @NotNull
    private Mpa mpa;

    /**
     * Метод для добавления жанра фильму
     * @param genre - жанр фильма
     */
    public void addFilmGenre(Genre genre) {
        genres.add(genre);
    }
}