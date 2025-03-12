package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.dto.IsSeatTakenDto;
import ru.job4j.cinema.model.*;

import javax.sql.DataSource;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    public static void init() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oTicketRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var dataSource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(dataSource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterAll
    public static void clearTickets() {
        sql2oTicketRepository.findAll().forEach(
                ticket -> sql2oTicketRepository
                        .delete(ticket.getId())
        );
    }

    @Test
    public void whenSaveTicketThenSave() {
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        IsSeatTakenDto isSeatTakenDto = new IsSeatTakenDto();
        User user = new User();
        Optional<Ticket> newTicket = sql2oTicketRepository.create(isSeatTakenDto, ticket, user);
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(List.of(newTicket));
    }
}