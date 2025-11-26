package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import ru.lkodos.servlet_util.RequestValidator;

import java.io.IOException;

@WebFilter(servletNames = {"GetTargetCurrencyServlet"})
public class GetTargetCurrencyValidatorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String code;
        try {
            code = request.getPathInfo().substring(1).toUpperCase();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid target currency code");
        }
        if (!RequestValidator.validate(code)) {
            throw new IllegalArgumentException("Invalid specific currency code. Currency code must consist of three Latin letters!");
        }
        request.setAttribute("code", code);
        filterChain.doFilter(request, servletResponse);
    }
}