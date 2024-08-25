package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository, FilmRepository filmRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        Collection<FilmSession> filmSessions = filmSessionRepository.findAll();
        return filmSessions.stream()
                .map(this::filmSessionToDto)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    public Optional<FilmSessionDto> filmSessionToDto(FilmSession filmsession) {
        Film film = filmRepository.findById(filmsession.getFilmId()).get();
        FilmSessionDto filmSessionDto = new FilmSessionDto(
                filmsession.getId(),
                film.getName(),
                filmsession.getHallsId(),
                filmsession.getStartTime(),
                filmsession.getEndTime(),
                filmsession.getPrice()
        );
        return Optional.ofNullable(filmSessionDto);
    }
}
