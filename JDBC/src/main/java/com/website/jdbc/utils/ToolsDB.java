package com.website.jdbc.utils;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Setter
@Getter
public class ToolsDB {

    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;

    public void closeDatabaseResources() {
        try {
            if (conn != null) {

                conn.close();
            }

            if (st != null) {

                st.close();
            }

            if (rs != null) {

                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

        } catch (SQLException se) {
            throw new IllegalStateException(se);
        }
    }
}
