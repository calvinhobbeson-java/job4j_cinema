package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.FilmSessionService;

import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {
    private FilmSessionService filmSessionService;
    private FilmSessionController filmSessionController;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenRequestGetAll() {
        var filmSession1  = new FilmSessionDto(1, "Some film", 1, LocalDateTime.parse("2024-07-18 19:40:00", formatter), LocalDateTime.parse("2024-07-18 19:40:00", formatter), 100);
        var filmSession2  = new FilmSessionDto(2, "some film 2", 2, LocalDateTime.parse("2024-07-18 19:40:00", formatter), LocalDateTime.parse("2024-07-18 19:40:00", formatter), 100);
        var expectedFilmSessions = List.of(filmSession1, filmSession2);
        when(filmSessionService.findAll()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("sessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessions);
    }
}