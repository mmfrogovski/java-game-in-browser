package com.game.java.model.jdbc;

import com.game.java.model.user.Room;

import java.util.Optional;

public class RoomDaoUmpl implements RoomDao {
    public static RoomDaoUmpl getInstance() {
        return RoomDaoUmpl.SingletonHolder.instance;
    }

    @Override
    public void save(Room room) {

    }

    @Override
    public Optional<Room> signIn() {
        return Optional.empty();
    }

    public static class SingletonHolder {
        private static final RoomDaoUmpl instance = new RoomDaoUmpl();
    }
}
