package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    long id;
    @NotNull
    @NotEmpty String name;
    @Size(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    String description;
    @NotNull LocalDate releaseDate;
    @Positive(message = "Продолжительность не может быть отрицательной")
    int duration;
    @Builder.Default
    Set<Long> likes = new TreeSet<>();
    private Mpa mpa;
    @Builder.Default
    private Set<Genre> genres = new HashSet<>();
}

