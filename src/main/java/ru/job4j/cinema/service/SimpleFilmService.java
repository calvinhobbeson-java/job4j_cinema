package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final FileService fileService;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository, FileService fileService) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.fileService = fileService;
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        Optional<Film> optional = filmRepository.findById(id);
        Film film = optional.get();
        return filmToDto(film);
    }

    @Override
    public Collection<FilmDto> findAll() {
        Collection<Film> films = filmRepository.findAll();
        return films.stream()
                .map(this::filmToDto)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    public Optional<FilmDto> filmToDto(Film film) {
        Genre genre = genreRepository.findById(film.getGenreId());
        FilmDto filmDto = new FilmDto(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getYear(),
                film.getMinimalAge(),
                film.getDurationInMinutes(),
                genre.getName(),
                fileService.getFileById(film.getId()).get());
        return Optional.ofNullable(filmDto);
    }
}
