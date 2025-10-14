package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.exception.ValidationException;

import java.io.IOException;

@WebFilter(servletNames = {"SpecificCurrencyServlet"})
public class SpecificCurrencyValidatorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String parameter = req.getPathInfo().substring(1).toUpperCase();
        if (parameter.length() != 3) {
//            resp.setStatus(400);
            resp.sendError(400, "Invalid specific currency parameter");
//            throw new ValidationException("Currency code is missing from the address");
        } else {
            req.setAttribute("code", parameter);
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}