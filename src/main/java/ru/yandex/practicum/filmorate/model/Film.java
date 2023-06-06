package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private long id;
    private @NotNull
    @NotEmpty String name;
    @Size(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    private String description;
    private @NotNull LocalDate releaseDate;
    @Positive(message = "Продолжительность не может быть отрицательной")
    private int duration;
}

