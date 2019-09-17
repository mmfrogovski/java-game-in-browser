package com.game.java.model.jdbc;

import java.util.Optional;

public interface UserDao {
    void save(User user);

    Optional<User> signIn(String login);

    void logOff(String login);

    Optional<User> getUserById(int id);
}
