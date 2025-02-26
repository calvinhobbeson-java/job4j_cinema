package ru.job4j.cinema.repository;


import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;
import org.sql2o.Sql2o;

import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO users (full_name, email, password) VALUES (:fullName, :email, :password)", true)
                    .addParameter("fullName", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email AND password = :password");
            var user = query.addParameter("email", email)
                    .addParameter("password", password)
                    .setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}
