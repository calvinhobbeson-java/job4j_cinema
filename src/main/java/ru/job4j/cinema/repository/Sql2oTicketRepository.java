package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.IsSeatTakenDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public Optional<Ticket> create(IsSeatTakenDto isSeatTakenDto, Ticket ticket, User user) {
        try (var connection = sql2o.open()) {
            var sql = "INSERT INTO tickets(session_id, row_number, place_number, user_id) VALUES (:sessionId, :rowNumber, :placeNumber, :userId)";
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", user.getId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(ticket);
    }

    @Override
    public boolean isSeatTaken(IsSeatTakenDto isSeatTakenDto) {
        try (var connection = sql2o.open()) {
            var sql = "SELECT * FROM tickets WHERE session_id = :sessionId AND row_number = :rowNumber AND place_number = :placeNumber";
            var query = connection.createQuery(sql);
            query.addParameter("sessionId", isSeatTakenDto.getSessionId());
            query.addParameter("rowNumber", isSeatTakenDto.getRowNumber());
            query.addParameter("placeNumber", isSeatTakenDto.getPlaceNumber());
            Ticket ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return ticket != null;
        }
    }
    @Override
    public boolean delete(int id) {
        boolean isDeleted;
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "DELETE FROM tickets WHERE id = :id"
            );
            query.addParameter("id", id);
            query.executeUpdate();
            isDeleted = connection.getResult() != 0;
        }
        return isDeleted;
    }

    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT * FROM tickets"
            );
            return query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        }
    }
}
