package com.game.java.model.jdbc;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    public void saveRoom(Room room);

    public Optional<Room> getRoomById(int id);

    public Optional<Room> getRoomByName(String name);

    public void deleteUserFromRoom(User user, Room room);

    public void addUserToRoom(User user, Room room);

    public List<Room> getAllRooms();

    public List<User> getUsersFromRoom(Room room);
}
