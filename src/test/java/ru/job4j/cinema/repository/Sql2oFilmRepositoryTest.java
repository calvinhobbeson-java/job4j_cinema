package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;

import javax.sql.DataSource;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void init() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFilmRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var dataSource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(dataSource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFindByIdThenReturnOptional() {
        Film expectedFilm = new Film(1, "Звездные войны. Эпизод 1: Скрытая угроза", "Приквел звездных войн, в котором есть настоящие бои на мечах.", 1999, 2, 12, 100, 1);
        assertThat(sql2oFilmRepository.findById(expectedFilm.getId()).get()).usingRecursiveComparison().isEqualTo(expectedFilm);
    }

    @Test
    public void whenFindAllThenFindSeven() {
        assertThat(sql2oFilmRepository.findAll()).hasSize(7);
    }
}