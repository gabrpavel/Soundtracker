package com.website.jdbc.dao;

import com.website.jdbc.model.User;
import com.website.jdbc.utils.DBConnection;
import com.website.jdbc.utils.ToolsDB;

import java.sql.*;
import java.util.*;

public class UsersDAO {

    private static final ToolsDB toolsDB = new ToolsDB();


    public List<User> getAllUsers()  {

        List<User> users = new ArrayList<>();

        try  {
            toolsDB.setConn(DBConnection.getConnection());
            toolsDB.setSt(toolsDB.getConn().createStatement());
            toolsDB.setRs(toolsDB.getSt().executeQuery("SELECT * FROM users"));
            while (toolsDB.getRs().next()) {
                User user = new User(toolsDB.getRs().getLong("user_id"),
                        toolsDB.getRs().getString("email"),
                        toolsDB.getRs().getString("password"),
                        toolsDB.getRs().getString("login"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            toolsDB.closeDatabaseResources();
        }
        return users;
    }

    public User getUserByID(Long id) {

        User user = null;

        try {
            toolsDB.setConn(DBConnection.getConnection());
            toolsDB.setPs(toolsDB.getConn()
                    .prepareStatement("SELECT * FROM users WHERE user_id = ?"));
            toolsDB.getPs().setLong(1, id);
            toolsDB.setRs(toolsDB.getPs().executeQuery());

            while(toolsDB.getRs().next()) {
                user = new User(toolsDB.getRs().getLong("user_id"),
                        toolsDB.getRs().getString("email"),
                        toolsDB.getRs().getString("password"),
                        toolsDB.getRs().getString("login"));

            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            toolsDB.closeDatabaseResources();
        }
        return user;
    }

    public int saveUser(User user) throws SQLException {

        int status = 0;

        try {
            toolsDB.setConn(DBConnection.getConnection());
            toolsDB.setPs(toolsDB.getConn()
                    .prepareStatement("INSERT INTO users(email, password, login) VALUES(?, ?, ?)"));
            toolsDB.getPs().setString(3, user.getLogin());
            toolsDB.getPs().setString(2, user.getPassword());
            toolsDB.getPs().setString(1, user.getEmail());
            status = toolsDB.getPs().executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            toolsDB.closeDatabaseResources();
        }
        return status;
    }

    public int updateUser(User user) throws SQLException {
        int status = 0;
        try {
            toolsDB.setConn(DBConnection.getConnection());
            toolsDB.setPs(toolsDB.getConn()
                    .prepareStatement("UPDATE users SET email=? WHERE user_id=?"));
            toolsDB.getPs().setString(1, user.getEmail());
            toolsDB.getPs().setLong(2, user.getId());
            status = toolsDB.getPs().executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return status;
    }

    public int deleteUser(Long userID) {
        int status = 0;
        try {
            toolsDB.setConn(DBConnection.getConnection());
            toolsDB.setPs(toolsDB.getConn()
                    .prepareStatement("DELETE FROM users where user_id=?"));
            toolsDB.getPs().setLong(1, userID);
            status = toolsDB.getPs().executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return status;
    }
}
