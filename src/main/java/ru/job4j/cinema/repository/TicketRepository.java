package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> create(Ticket ticket);

    boolean isSeatTaken(int sessionId, int rowNumber,int seatNumber);
}