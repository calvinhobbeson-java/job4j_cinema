package ru.job4j.cinema.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.IsSeatTakenDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

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
        FilmSessionDto filmSessionDto = filmSessionService.findById(filmSessionId);
        model.addAttribute("filmSessionDto", filmSessionDto);
        return "tickets/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute IsSeatTakenDto isSeatTakenDto, @ModelAttribute Ticket ticket, Model model, HttpSession session) {
        try {
            ticketService.create(isSeatTakenDto, ticket, session);
            return "tickets/success";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "tickets/fail";
        }
    }
}
