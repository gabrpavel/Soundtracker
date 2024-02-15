package com.websitte.JDBC.utils;

import java.sql.*;

public class DBConnection {

    public static Connection getConnection() {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/website_db",
                    "postgres", "admin");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
