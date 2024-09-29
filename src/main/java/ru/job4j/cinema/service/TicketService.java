package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface TicketService {

    Optional<Ticket> create(Ticket ticket, HttpSession session);

    boolean isSeatTaken(int sessionId, int rowNumber,int seatNumber);
}
