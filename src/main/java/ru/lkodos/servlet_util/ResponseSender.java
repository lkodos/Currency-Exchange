package ru.lkodos.servlet_util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseSender {

    public static void send(HttpServletResponse resp, Object obj) throws IOException {
        String response = new Gson().toJson(obj);
        resp.getWriter().write(response);
    }
}