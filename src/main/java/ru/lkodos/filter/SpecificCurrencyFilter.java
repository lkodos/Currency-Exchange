package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.exception.ValidationException;

import java.io.IOException;

@WebFilter("/currencies/*")
public class SpecificCurrencyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String currencyCode = req.getPathInfo().substring(1).toUpperCase();
        System.out.println(currencyCode);
//        if (currencyCode.isEmpty()) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
        if (currencyCode.length() != 3) {
            throw new ValidationException("Currency code is missing in the address");
        } else {
            servletRequest.setAttribute("code", currencyCode);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
