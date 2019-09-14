package com.game.java.model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {
    private final String SQL_QUERY_GET_ALL_ROOMS = "SELECT id, name, firstUserId, secondUserId FROM rooms";
    private final String SQL_QUERY_ADD_ROOM = "INSERT INTO rooms (name) VALUES(?)";
    private final String SQL_QUERY_ADD_FIRST_USER_TO_ROOM = "UPDATE rooms SET firstUserId = ? WHERE id = ?";
    private final String SQL_QUERY_ADD_SECOND_USER_TO_ROOM = "UPDATE rooms SET secondUserId = ? WHERE id = ?";
    private final String SQL_QUERY_DELETE_USER = "INSERT INTO rooms (name) VALUES(?)";
    private final String SQL_QUERY_GET_ROOM_BY_ID = "SELECT id, name, firstUserId, secondUserId FROM rooms WHERE id = ?";
    private final String SQL_QUERY_GET_ROOM_BY_NAME = "SELECT id, name, firstUserId, secondUserId FROM rooms WHERE name = ?";


    public static RoomDaoImpl getInstance() {
        return RoomDaoImpl.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final RoomDaoImpl instance = new RoomDaoImpl();
    }

    @Override
    public List<Room> getAllRooms() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Room> rooms = new ArrayList<>();
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_ALL_ROOMS);
            resultSet = preparedStatement.executeQuery();
            return initRooms(resultSet, rooms);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rooms;
    }

    @Override
    public void saveRoom(Room room) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_ROOM);
            preparedStatement.setString(1, room.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        } finally {
            DatabaseConnection.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public Optional<Room> getRoomById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_ROOM_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return initRoom(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Room> getRoomByName(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_ROOM_BY_NAME);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            return initRoom(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private Optional<Room> initRoom(ResultSet resultSet) throws SQLException {
        Room room = null;
        if (resultSet.next()) {
            room = new Room();
            room.setId(resultSet.getInt("Id"));
            room.setName(resultSet.getString("Name"));
            room.setName(resultSet.getString("FirstUserId"));
            room.setName(resultSet.getString("SecondUserId"));

        }
        return Optional.ofNullable(room);
    }

    private List<Room> initRooms(ResultSet resultSet, List<Room> rooms) throws SQLException {
        while (resultSet.next()) {
            Room room = new Room();
            room.setId(resultSet.getInt("Id"));
            room.setName(resultSet.getString("Name"));
            room.setFirstUserId(resultSet.getInt("FirstUserId"));
            room.setSecondUserId(resultSet.getInt("SecondUserId"));
            rooms.add(room);
        }
        return rooms;
    }

    @Override
    public void deleteUserFromRoom(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_DELETE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        } finally {
            DatabaseConnection.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void addUserToRoom(User user, int roomId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            Optional<Room> room = getRoomById(roomId);
            if(room.isPresent()) {
                if(room.get().getFirstUserId()!=0) {
                    preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_FIRST_USER_TO_ROOM);
                    preparedStatement.setInt(1, user.getId());
                    preparedStatement.setInt(2, roomId);
                    preparedStatement.executeUpdate();
                } else {
                    preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_SECOND_USER_TO_ROOM);
                    preparedStatement.setInt(1, user.getId());
                    preparedStatement.setInt(2, roomId);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error");
        } finally {
            DatabaseConnection.closeConnection(connection, preparedStatement);
        }
    }
}
