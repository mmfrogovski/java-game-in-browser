package com.game.java.model.jdbc;

import com.game.java.model.user.Room;
import com.game.java.model.user.User;

import java.util.Optional;

public interface RoomDao {
    public void save(Room room);

    public Optional<Room> signIn();
}
