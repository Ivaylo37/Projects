package com.scalefocus.util.db;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DriverManager {

    protected static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    protected static void logSqlException(SQLException e){
        logger.severe("Message: " + e.getMessage());
        logger.severe("SQL state: " + e.getSQLState());
        logger.severe("SQL Code: " + e.getErrorCode());
        logger.severe("Next exception: " + e.getNextException());
    }
}
