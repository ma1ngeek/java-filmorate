package ru.film.rate.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private @NotNull @NotEmpty @Email String email;
    private @NotNull @NotEmpty String login;
    private String name;
    @Past
    private LocalDate birthday;

}
