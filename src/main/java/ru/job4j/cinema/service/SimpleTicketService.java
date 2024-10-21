package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.IsSeatTakenDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
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
    public Optional<Ticket> create(IsSeatTakenDto isSeatTakenDto, Ticket ticket, HttpSession session) {
        return ticketRepository.create(isSeatTakenDto, ticket,session);
    }

    @Override
    public boolean isSeatTaken(IsSeatTakenDto isSeatTakenDto) {
        return ticketRepository.isSeatTaken(isSeatTakenDto);
    }
}
