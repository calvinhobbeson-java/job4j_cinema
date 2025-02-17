package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.IsSeatTakenDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface TicketService {

    Optional<Ticket> create(IsSeatTakenDto isSeatTakenDto, Ticket ticket, User user);

    boolean isSeatTaken(IsSeatTakenDto isSeatTakenDto);
}
