package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);
        }
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var filmSessionDto = query.setColumnMappings(FilmSessionDto.COLUMN_MAPPING).executeAndFetchFirst(FilmSessionDto.class);
            return Optional.ofNullable(filmSessionDto);
        }
    }
}
