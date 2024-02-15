package com.websitte.JDBC.dao;

import com.websitte.JDBC.model.User;
import com.websitte.JDBC.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class UsersDAO {

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public int saveUser(User user) {
        int status = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO users(email, password, login) VALUES(?, ?, ?)");
            //preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(1, user.getEmail());
            status = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
