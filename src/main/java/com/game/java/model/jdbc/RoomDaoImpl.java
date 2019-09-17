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
    private final String SQL_QUERY_DELETE_FIRST_USER_FROM_ROOM = "UPDATE rooms SET firstUserId = ? WHERE id = ?";
    private final String SQL_QUERY_DELETE_SECOND_USER_FROM_ROOM = "UPDATE rooms SET secondUserId = ? WHERE id = ?";
    private final String SQL_QUERY_GET_ROOM_BY_ID = "SELECT id, name, firstUserId, secondUserId FROM rooms WHERE id = ?";
    private final String SQL_QUERY_GET_ROOM_BY_NAME = "SELECT id, name, firstUserId, secondUserId FROM rooms WHERE name = ?";
    private final String SQL_QUERY_CHANGE_IN_ROOM = "UPDATE users SET inroom = ? WHERE id = ?";
    private final String SQL_QUERY_GET_FIRST_USER_ID = "SELECT firstuserid FROM rooms WHERE id = ?";
    private final String SQL_QUERY_GET_USERS_IN_ROOM = "Select * from users where id in(select firstuserid from rooms where id = ?)union Select * from users where id in(select seconduserid from rooms where id = ?)";
    private final String SQL_QUERY_DELETE_ROOM = "DELETE FROM rooms WHERE id = ?";


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
            room = forInitRoom(resultSet);
        }
        return Optional.ofNullable(room);
    }

    private List<Room> initRooms(ResultSet resultSet, List<Room> rooms) throws SQLException {
        while (resultSet.next()) {
            rooms.add(forInitRoom(resultSet));
        }
        return rooms;
    }

    private Room forInitRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getInt("Id"));
        room.setName(resultSet.getString("Name"));
        room.setFirstUserId(resultSet.getInt("FirstUserId"));
        room.setSecondUserId(resultSet.getInt("SecondUserId"));
        return room;
    }

    @Override
    public void deleteUserFromRoom(User user, Room room) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_FIRST_USER_ID);
            preparedStatement.setInt(1, room.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String sqlQuery;
            if (user.getId() == resultSet.getInt("FirstUserId")) {
                sqlQuery = SQL_QUERY_ADD_FIRST_USER_TO_ROOM;
            } else {
                sqlQuery = SQL_QUERY_ADD_SECOND_USER_TO_ROOM;
            }
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, room.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(SQL_QUERY_CHANGE_IN_ROOM);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
            List<User> users = getUsersFromRoom(room);
            if (users.isEmpty()) {
                connection = DatabaseConnection.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(SQL_QUERY_DELETE_ROOM);
                preparedStatement.setInt(1, room.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error");
        } finally {
            DatabaseConnection.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void addUserToRoom(User user, Room room) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            String sqlQuery;
            if (room.getFirstUserId() == 0) {
                sqlQuery = SQL_QUERY_ADD_FIRST_USER_TO_ROOM;

            } else {
                sqlQuery = SQL_QUERY_ADD_SECOND_USER_TO_ROOM;
            }
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, room.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(SQL_QUERY_CHANGE_IN_ROOM);
            preparedStatement.setInt(1, room.getId());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error");
        } finally {
            DatabaseConnection.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<User> getUsersFromRoom(Room room) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_USERS_IN_ROOM);
            preparedStatement.setInt(1, room.getId());
            preparedStatement.setInt(2, room.getId());
            resultSet = preparedStatement.executeQuery();
            return initUsers(resultSet, users);
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
        return users;
    }

    private List<User> initUsers(ResultSet resultSet, List<User> users) throws SQLException {
        while (resultSet.next()) {
            users.add(forInitUser(resultSet));
        }
        return users;
    }

    private User forInitUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("Id"));
        user.setLogin(resultSet.getString("Login"));
        user.setPassword(resultSet.getString("Password"));
        user.setAuthoried(resultSet.getBoolean("Authorized"));
        user.setInRoom(resultSet.getInt("InRoom"));
        user.setInGame(resultSet.getBoolean("InGame"));
        return user;
    }
}
