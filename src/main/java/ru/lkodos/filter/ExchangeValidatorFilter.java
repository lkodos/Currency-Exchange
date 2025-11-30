package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import ru.lkodos.servlet_util.RequestValidator;

import java.io.IOException;

@WebFilter("/exchange")
public class ExchangeValidatorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String from = servletRequest.getParameter("from");
        String to = servletRequest.getParameter("to");
        String amount = servletRequest.getParameter("amount");

        if (!RequestValidator.validate(from) || !RequestValidator.validate(to) || amount.isEmpty()) {
            throw new IllegalArgumentException("Required parameter is missing");
        }

        servletRequest.setAttribute("from", from);
        servletRequest.setAttribute("to", to);
        servletRequest.setAttribute("amount", amount);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}