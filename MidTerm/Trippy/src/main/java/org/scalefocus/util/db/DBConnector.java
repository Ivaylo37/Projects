package org.scalefocus.util.db;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector extends DriverManager {

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASSWORD = "Ivcata99";

    public DBConnector()  {

    }
    public Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}