package ru.lkodos.util;

import ru.lkodos.exception.DbAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static final String URL = "db.url";
    private static final String DRIVER = "db.driver";

    static {
        loadDriver();
    }

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.getProperties(URL));
        } catch (SQLException e) {
            throw new DbAccessException("Database is unavaliable", e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.getProperties(DRIVER));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}