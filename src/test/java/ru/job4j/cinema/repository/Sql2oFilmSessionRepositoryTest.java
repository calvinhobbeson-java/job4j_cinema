package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;

import javax.sql.DataSource;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void init() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFilmSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var dataSource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(dataSource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }
    @Test
    public void whenFindByIdThenReturnSession() {
        FilmSession expectedFilmSession = new FilmSession(1, 1, 1, LocalDateTime.parse("2024-07-18 19:40:00", formatter), LocalDateTime.parse("2024-07-18 19:40:00", formatter), 100);
        assertThat(sql2oFilmSessionRepository.findById(expectedFilmSession.getId()).get()).usingRecursiveComparison().isEqualTo(expectedFilmSession);
    }

    @Test
    public void whenFindAllThenFindEight() {
        assertThat(sql2oFilmSessionRepository.findAll()).hasSize(8);
    }

}