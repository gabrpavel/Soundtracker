package com.websitte.jdbc.dao;

import com.websitte.jdbc.model.User;
import com.websitte.jdbc.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class UsersDAO {

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        try  {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User(resultSet.getLong("user_id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("login"));
                users.add(user);
            }
            DBConnection.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public int saveUser(User user) {
        int status = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO "users"(email, password, login) VALUES(?, ?, ?)");
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(1, user.getEmail());
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public int updateUser(User user) {
        int status = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE users SET login=?, email=?, password=?");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPassword());
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public int deleteUser(Long userID) {
        int status = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM users where id=?");
            preparedStatement.setLong(1, userID);
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
}
