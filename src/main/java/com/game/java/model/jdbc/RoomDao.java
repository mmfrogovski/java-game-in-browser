package com.game.java.model.jdbc;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    public void saveRoom(Room room);

    public Optional<Room> getRoomById(int id);
    public Optional<Room> getRoomByName(String name);

    public void deleteUserFromRoom(User user);

    public void addUserToRoom(User user, int roomId);

    public List<Room> getAllRooms();
}
