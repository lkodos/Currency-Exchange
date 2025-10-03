package ru.lkodos.filter;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.exception.DbAccessException;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class ExceptionHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {
            resp.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (DbAccessException e) {
            int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            resp.setStatus(statusCode);
            sendJsonResponse(resp, new ErrorConfig(statusCode, e.getMessage()));
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, ErrorConfig errorConfig) {
        String json = new Gson().toJson(errorConfig);
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ErrorConfig {
        int statusCode;
        String message;

        public ErrorConfig(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }
    }
}