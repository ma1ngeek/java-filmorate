package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> findAll() {
        final String findAll = "SELECT * FROM mparate";
        return jdbcTemplate.query(findAll, this::mpaMaker);
    }

    @Override
    public Mpa getById(int id) {
        final String getMpa = "SELECT * FROM mparate WHERE id= ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(getMpa, id);
        if (!sqlRowSet.next()) {
            log.info("Рейтинг не найден", id);
            throw new NotFoundException("Рейтинг не найден");
        }
        return jdbcTemplate.queryForObject(getMpa, this::mpaMaker, id);
    }

    private Mpa mpaMaker(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Mpa(id, name);
    }
}