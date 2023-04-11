package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс фильм
 * @author Мелентьев Сергей
 * @version 1.0
 */
@Data
@NonNull
@Builder(toBuilder = true)
@AllArgsConstructor
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

    /** Поле с уникальными идентификаторами пользователей, поставивших лайк под фильмом */
    private final Set<Integer> likes = new HashSet<>();

    /**
     * Метод для добавления лайка фильму
     * @param id - идентификатор пользователя
     */
    public void addLike(int id) {
        likes.add(id);
    }

    /**
     * Метод для удаления лайка у фильма
     * @param id - идентификатор пользователя
     */
    public void removeLike(int id) {
        likes.remove(id);
    }

    public int getLikesCount() {
        return likes.size();
    }
}
