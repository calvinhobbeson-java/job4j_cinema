package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface UserService {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
