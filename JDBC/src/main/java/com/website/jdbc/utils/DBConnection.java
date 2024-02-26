package com.website.jdbc.utils;

import java.sql.*;

public class DBConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/website_db";
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private DBConnection() {
        throw new IllegalStateException("Utility class");
    }


    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }

        return DriverManager.getConnection(DB_URL,
                DB_USER, DB_PASSWORD);
    }
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
