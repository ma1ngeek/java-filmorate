package ru.film.rate.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private long id;
    private @NotNull @NotEmpty String name;
    @Size(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    private String description;
    private @NotNull LocalDate releaseDate;
    @Positive(message = "Продолжительность не может быть отрицательной")
    private int duration;

}

