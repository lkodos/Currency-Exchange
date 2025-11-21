package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.entity.Error;
import ru.lkodos.exception.CurrencyAlreadyExistsException;
import ru.lkodos.exception.CurrencyNotFoundException;
import ru.lkodos.exception.DbAccessException;
import ru.lkodos.servlet_util.ResponseSender;

import java.io.IOException;

@WebFilter("/*")
public class ErrorHandlerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (DbAccessException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ResponseSender.send(response, new Error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ResponseSender.send(response, new Error(e.getMessage()));
        } catch (CurrencyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ResponseSender.send(response, new Error(e.getMessage()));
        } catch (CurrencyAlreadyExistsException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            ResponseSender.send(response, new Error(e.getMessage()));
        }
    }
}