package ru.lkodos.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.lkodos.exception.DbAccessException;

import javax.sql.DataSource;
import java.sql.Connection;

public class ConnectionManager {

    private static final String URL = "db.url";
    private static final String MAX_POOL_SIZE = "max.pool.size";
    private static final String MIN_IDLE = "min.idle";
    private static final String IDLE_TIMEOUT = "idle.timeout";
    private static final String MAX_LIFE_TIME = "max.life.time";
    private static DataSource dataSource = null;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(PropertiesUtil.getProperties(URL));
            config.setMaximumPoolSize(Integer.parseInt(PropertiesUtil.getProperties(MAX_POOL_SIZE)));
            config.setMaximumPoolSize(Integer.parseInt(PropertiesUtil.getProperties(MIN_IDLE)));
            config.setMaximumPoolSize(Integer.parseInt(PropertiesUtil.getProperties(IDLE_TIMEOUT)));
            config.setMaximumPoolSize(Integer.parseInt(PropertiesUtil.getProperties(MAX_LIFE_TIME)));
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
        } catch (Exception e) {
            throw new DbAccessException("Database is unavailable", e);
        }
    }
}