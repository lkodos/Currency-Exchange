package ru.lkodos.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.lkodos.exception.DbAccessException;
import ru.lkodos.exception.PropExeption;

import javax.sql.DataSource;
import java.sql.Connection;

public class ConnectionManager {

    private static final String URL = "db.url";
    private static final String DRIVER = "db.driver";
    private static DataSource dataSource = null;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(PropertiesUtil.getProperties(URL));
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
//            MUST BE LOGGING
        }
    }

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (PropExeption e) {
            throw new DbAccessException("sddsd", e);
        } catch (Exception e) {
            throw new DbAccessException("Database is unavaliable", e);
        }
    }
}