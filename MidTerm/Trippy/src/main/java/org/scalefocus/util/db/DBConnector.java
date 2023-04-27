package org.scalefocus.util.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector extends DriverManager{

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASSWORD = "Ivcata99";
    private static Connection connection;

    private DBConnector()  {
        try {
            if (connection == null || connection.isClosed()){
                java.sql.DriverManager.setLogWriter(new PrintWriter((System.out)));
                try {
                    connection = java.sql.DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                } catch (SQLException e) {
                    logSqlException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {
        DBConnector dbConnector = new DBConnector();
        return connection;
    }
}