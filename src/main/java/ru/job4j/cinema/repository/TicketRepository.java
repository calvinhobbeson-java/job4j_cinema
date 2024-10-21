package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.IsSeatTakenDto;
import ru.job4j.cinema.model.Ticket;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> create(IsSeatTakenDto isSeatTakenDto, Ticket ticket, HttpSession session);

    boolean isSeatTaken(IsSeatTakenDto isSeatTakenDto);
}
