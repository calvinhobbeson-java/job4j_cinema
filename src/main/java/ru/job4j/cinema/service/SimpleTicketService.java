package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> create(Ticket ticket, HttpSession session) {
        int userId = Integer.parseInt((String) session.getAttribute("id"));
        ticket.setId(userId);
        return ticketRepository.create(ticket);
    }

    @Override
    public boolean isSeatTaken(int sessionId, int rowNumber, int seatNumber) {
        return ticketRepository.isSeatTaken(sessionId, rowNumber, seatNumber);
    }
}
