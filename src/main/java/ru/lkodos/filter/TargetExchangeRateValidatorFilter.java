package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import ru.lkodos.servlet_util.RequestValidator;

import java.io.IOException;

@WebFilter(servletNames = {"TargetExchangeRateServlet"})
public class TargetExchangeRateValidatorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String fullCode;
        try {
            fullCode = request.getPathInfo().substring(1).toUpperCase();
        } catch (Exception e) {
            throw new IllegalArgumentException("Currency pair codes are missing in the address");
        }
        if (!RequestValidator.validatePair(fullCode)) {
            throw new IllegalArgumentException("Currency pair codes are missing in the address");
        }
        String baseCurrencyCode = fullCode.substring(0, 3);
        String targetCurrencyCode = fullCode.substring(3);
        request.setAttribute("baseCurrencyCode", baseCurrencyCode);
        request.setAttribute("targetCurrencyCode", targetCurrencyCode);
        filterChain.doFilter(request, servletResponse);
    }
}