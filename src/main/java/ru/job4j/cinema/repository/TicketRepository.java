package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.IsSeatTakenDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> create(IsSeatTakenDto isSeatTakenDto, Ticket ticket, User user);

    boolean isSeatTaken(IsSeatTakenDto isSeatTakenDto);

    boolean delete(int id);

    Collection<Ticket> findAll();
}
