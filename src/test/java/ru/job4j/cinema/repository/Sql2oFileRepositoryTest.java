package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;

import javax.sql.DataSource;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository sql2oFileRepository;

    @BeforeAll
    public static void init() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFileRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var dataSource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(dataSource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);

    }
    @Test
    public void whenFindByIdThenReturnOptional() {
        File expectedFile = new File("Crow", "files/ворон.jpg");
        assertThat(sql2oFileRepository.findById(expectedFile.getId()).get()).usingRecursiveComparison().isEqualTo(expectedFile);
    }
}