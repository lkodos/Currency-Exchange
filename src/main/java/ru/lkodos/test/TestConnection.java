package ru.lkodos.test;

import ru.lkodos.util.ConnectionManager;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection connection = ConnectionManager.getDataSource().getConnection();
            connection.getCatalog();
            System.out.println("Connection SUCCEEDED");
        } catch (Exception e) {
            System.out.println("Connection FAILED");
            System.out.println(e.getMessage());
        }
    }
}
