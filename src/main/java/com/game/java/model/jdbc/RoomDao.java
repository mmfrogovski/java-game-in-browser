package com.game.java.model.jdbc;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    void saveRoom(Room room);

    Optional<Room> getRoomById(int id);

    Optional<Room> getRoomByName(String name);

    void deleteUserFromRoom(User user, Room room);

    void addUserToRoom(User user, Room room);

    List<Room> getAllRooms();

    List<User> getUsersFromRoom(Room room);
}
