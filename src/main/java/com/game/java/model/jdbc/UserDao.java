package com.game.java.model.jdbc;

import com.game.java.model.user.User;

import java.util.Optional;

public interface UserDao {
    public void save(User user);

    public Optional<User> signIn(String login);
}
