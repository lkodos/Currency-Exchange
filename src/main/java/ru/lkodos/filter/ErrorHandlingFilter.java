package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.exception.DbAccessException;
import ru.lkodos.exception.ValidationException;

import java.io.IOException;

@WebFilter("/*")
public class ErrorHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        try {
            resp.setStatus(HttpServletResponse.SC_OK);   // 200
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (DbAccessException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);   // 500
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);   // 400
        }
    }
}
