package com.scalefocus.util.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector extends DriverManager{
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASSWORD = "Ivcata99";
    private static DBConnector instance;
    private Connection connection;


    public static DBConnector getInstance(){
        if (instance == null){
            instance = new DBConnector();
        }
        return instance;
    }

    private DBConnector(){
        if (connection == null){
            java.sql.DriverManager.setLogWriter(new PrintWriter((System.out)));
            try {
                connection = java.sql.DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            } catch (SQLException e) {
                logSqlException(e);
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
