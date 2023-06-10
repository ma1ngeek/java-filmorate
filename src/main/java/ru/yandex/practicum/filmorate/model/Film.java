package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {
    private long id;
    private @NotNull
    @NotEmpty String name;
    @Size(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    private String description;
    private @NotNull LocalDate releaseDate;
    @Positive(message = "Продолжительность не может быть отрицательной")
    private int duration;
    private Set<Long> likes = new TreeSet<>();
}

