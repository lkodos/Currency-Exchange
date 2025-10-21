package ru.lkodos.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class Sender {

    public static void send(HttpServletResponse servletResponse, Object o) {

        String json = new Gson().toJson(o);
        try (PrintWriter out = servletResponse.getWriter()) {
            out.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}