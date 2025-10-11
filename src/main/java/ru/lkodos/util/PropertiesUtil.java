package ru.lkodos.util;

import ru.lkodos.exception.DbAccessException;
import ru.lkodos.exception.PropExeption;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            loadProperties();
        } catch (Exception e) {
//            MUST BE LOGGING
        }
    }

    private PropertiesUtil() {
    }

    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("app1.properties")) {
            PROPERTIES.load(inputStream);
        } catch (Exception e) {
            throw new PropExeption("Error loading properties", e);
        }
    }
}