package com.scalefocus.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariPoolDataSource extends DriverManager {

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASSWORD = "Ivcata99";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static{
        config.setJdbcUrl(DATABASE_URL);
        config.setUsername(DATABASE_USER);
        config.setPassword(DATABASE_PASSWORD);
        config.setMaximumPoolSize(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void main(String[] args) {
        try {
            Connection connection = HikariPoolDataSource.getConnection();
            logger.info("Connection to Hikari is closed: " + connection.isClosed());
        } catch (SQLException e) {
            logSqlException(e);
        }
    }
}