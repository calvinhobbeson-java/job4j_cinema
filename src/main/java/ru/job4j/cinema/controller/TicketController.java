package ru.job4j.cinema.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;


    public TicketController(TicketService ticketService, FilmSessionService filmSessionService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/create")
    public String getCreationPage(@RequestParam("filmSessionId") int filmSessionId, Model model) {
        Optional<FilmSessionDto> filmSessionDto = filmSessionService.findById(filmSessionId);
        model.addAttribute("filmSessionDto", filmSessionDto);
        return "tickets/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Ticket ticket, @RequestParam int sessionId, @RequestParam int rowNumber, @RequestParam int seatNumber,  Model model, HttpSession session) {
        if (ticketService.isSeatTaken(sessionId, rowNumber, seatNumber)) {
            return "tickets/fail";
        }
        try {
            ticketService.create(ticket, session);
            return "redirect:/tickets/success";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "tickets/fail";
        }
    }
}
