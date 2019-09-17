package com.game.java.model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final String SQL_QUERY_ADD_USER = "INSERT INTO users (login, password, authorized) VALUES(?,?,?)";
    private final String SQL_QUERY_GET_USER = "SELECT * FROM users WHERE login = ?";
    private final String SQL_QUERY_GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final String SQL_QUERY_CHANGE_AUTHORIZED = "UPDATE users SET authorized = ? WHERE login = ?";

    public UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final UserDaoImpl instance = new UserDaoImpl();
    }

    @Override
    public void save(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, false);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        } finally {
            DatabaseConnection.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public Optional<User> signIn(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_USER);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement(SQL_QUERY_CHANGE_AUTHORIZED);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            return initUser(resultSet);
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
    public void logOff(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_CHANGE_AUTHORIZED);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
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
    }

    @Override
    public Optional<User> getUserById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return initUser(resultSet);
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

    private Optional<User> initUser(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet.next()) {
            user = init(resultSet);
        }
        return Optional.ofNullable(user);
    }

    private User init(ResultSet resultSet) throws SQLException {
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