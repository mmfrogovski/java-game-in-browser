package com.game.java.model.jdbc;

import java.util.Optional;

public interface UserDao {
    public void save(User user);

    public Optional<User> signIn(String login);

    public void logOff(String login);
}
