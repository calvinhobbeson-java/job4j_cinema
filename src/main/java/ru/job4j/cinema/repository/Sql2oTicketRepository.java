package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> create(Ticket ticket) {
        try (var connection  = sql2o.open()) {
            var sql = "INSERT INTO tickets(sessionId, rowNumber, placeNumber, userId) VALUES (:sessionId, :rowNumber, :placeNumber, :userId)";
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
        }
        return Optional.of(ticket);
    }

    @Override
    public boolean isSeatTaken(int sessionId, int rowNumber,int seatNumber) {
        try (var connection = sql2o.open()) {
            var sql = "SELECT * FROM tickets WHERE sessionId = sessionId, rowNumber = rowNumber, seatNumber = seatNumber";
            var query = connection.createQuery(sql);
            query.addParameter("sessionId", sessionId);
            query.addParameter("rowNumber", rowNumber);
            query.addParameter("seatNumber", seatNumber);
            Ticket ticket = query.executeAndFetchFirst(Ticket.class);
            return ticket != null;
        }
    }
}
