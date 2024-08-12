package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

import java.util.Optional;
import java.util.Collection;

public interface FilmRepository {

    Optional<Film> findById(int id);

    Collection<Film> findAll();
}
