package com.game.java.model.jdbc;

import com.game.java.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final String SQL_QUERY_ADD_USER = "INSERT INTO users (login, password) VALUES(?,?)";
    private final String SQL_QUERY_GET_USER = "SELECT id, login, password FROM users " +
            "WHERE login = ?";

    public UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return SingletonHolder.instance;
    }

    public static class SingletonHolder {
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        } finally {
            try {

                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Error");
            }
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
        if(resultSet.next()){
            user = new User();
            user.setId(resultSet.getInt("Id"));
            user.setLogin(resultSet.getString("Login"));
            user.setPassword(resultSet.getString("Password"));
        }
        return Optional.ofNullable(user);
    }
}