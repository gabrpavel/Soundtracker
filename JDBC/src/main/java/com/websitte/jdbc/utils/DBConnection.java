package com.websitte.jdbc.utils;

import java.sql.*;

public class DBConnection {

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/website_db",
                    "postgres", System.getenv("admin"));
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
