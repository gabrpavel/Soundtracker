package com.websitte.jdbc.dao;

import com.websitte.jdbc.model.User;
import com.websitte.jdbc.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class UsersDAO {

    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;

    private void closeDatabaseResources() {
        try {
            if(conn != null) {

                conn.close();
            }

            if(st != null) {

                st.close();
            }

            if(rs != null) {

                rs.close();
            }

            if(ps != null) {
                ps.close();
            }

        } catch(SQLException se) {
            throw new IllegalStateException(se);
        }
    }
    public List<User> getAllUsers()  {

        List<User> users = new ArrayList<>();

        try  {
            conn = DBConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User user = new User(rs.getLong("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("login"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            closeDatabaseResources();
        }
        return users;
    }

    public User getUserByID(Long id) {

        User user = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn
                    .prepareStatement("SELECT * FROM users WHERE user_id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                user = new User(rs.getLong("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("login"));

            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            closeDatabaseResources();
        }
        return user;
    }

    public int saveUser(User user) throws SQLException {

        int status = 0;

        try {
            conn = DBConnection.getConnection();
            ps = conn
                    .prepareStatement("INSERT INTO users(email, password, login) VALUES(?, ?, ?)");
            ps.setString(3, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(1, user.getEmail());
            status = ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            closeDatabaseResources();
        }
        return status;
    }

    public int updateUser(User user) throws SQLException {
        int status = 0;
        try {
            conn = DBConnection.getConnection();
            ps = conn
                    .prepareStatement("UPDATE users SET email=? WHERE user_id=?");
            ps.setString(1, user.getEmail());
            ps.setLong(2, user.getId());
            status = ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return status;
    }

    public int deleteUser(Long userID) {
        int status = 0;
        try {
            conn = DBConnection.getConnection();
            ps = conn
                    .prepareStatement("DELETE FROM users where user_id=?");
            ps.setLong(1, userID);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return status;
    }
}
