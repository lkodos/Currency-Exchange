package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.exception.DbAccessException;

import java.io.IOException;

@WebFilter("/*")
public class ErrorHandlerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (DbAccessException e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.sendError(HttpServletResponse.SC_CREATED, e.getMessage());
        }
    }

    private void showErrorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/500.jsp");
        requestDispatcher.forward(req, resp);
    }
}