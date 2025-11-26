package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import ru.lkodos.servlet_util.RequestValidator;

import java.io.IOException;

@WebFilter(servletNames = {"ExchangeRateServlet"})
public class SaveExchangeRateValidatorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if ("POST".equalsIgnoreCase((request).getMethod())) {
            String baseCurrencyCode = request.getParameter("baseCurrencyCode");
            String targetCurrencyCode = request.getParameter("targetCurrencyCode");
            String rate = request.getParameter("rate");

            if (rate.isEmpty()) {
                throw new IllegalArgumentException("Required form field is missing");
            }
            if (!RequestValidator.validate(baseCurrencyCode) || !RequestValidator.validate(targetCurrencyCode)) {
                throw new IllegalArgumentException("Invalid currency code. Currency code must consist of three Latin letters!");
            }
            request.setAttribute("baseCurrencyCode", baseCurrencyCode);
            request.setAttribute("targetCurrencyCode", targetCurrencyCode);
            request.setAttribute("rate", rate);
        }
        filterChain.doFilter(request, servletResponse);
    }
}