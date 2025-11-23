package ru.lkodos.servlet_util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseSender {

    private static final Gson gson = new Gson();

    private ResponseSender() {
    }

    public static void send(HttpServletResponse resp, Object obj) throws IOException {
        String response = gson.toJson(obj);
        resp.getWriter().write(response);
        resp.getWriter().flush();
    }
}