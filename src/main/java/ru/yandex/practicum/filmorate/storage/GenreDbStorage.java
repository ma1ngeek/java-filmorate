package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        final String findAll = "SELECT * FROM genres";
        return jdbcTemplate.query(findAll, this::genreMaker);
    }

    @Override
    public Genre getById(int id) {
        final String getGenre = "SELECT * FROM genres WHERE id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(getGenre, id);
        if (!sqlRowSet.next()) {
            log.info("Жанр не найден", id);
            throw new NotFoundException("Жанр не найден");
        }
        final String sqlQuery = "SELECT * FROM genres WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::genreMaker, id);
    }

    private Genre genreMaker(ResultSet rs, int rowNum) throws SQLException {
        final int id = rs.getInt("id");
        final String name = rs.getString("name");
        return new Genre(id, name);
    }
}
