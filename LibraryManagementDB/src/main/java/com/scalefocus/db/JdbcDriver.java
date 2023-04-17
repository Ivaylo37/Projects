package com.scalefocus.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDriver extends DriverManager {

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASSWORD = "Ivcata99";

    public static void main(String[] args) {
        try(Connection connection = JdbcDriver.getConnection()){
            logger.info("Connection is closed: " + connection.isClosed());
        }catch (SQLException e){
            logSqlException(e);
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            java.sql.DriverManager.setLogWriter(new PrintWriter((System.out)));
            connection = java.sql.DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            logSqlException(e);
        }
        return connection;
    }
}