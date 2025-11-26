package ru.lkodos.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import ru.lkodos.servlet_util.RequestValidator;

import java.io.IOException;

@WebFilter(servletNames = {"CurrencyServlet"})
public class SaveCurrencyValidatorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if ("POST".equalsIgnoreCase((request).getMethod())) {
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            String sign = request.getParameter("sign");

            if (name.isEmpty() || code.isEmpty()) {
                throw new IllegalArgumentException("Required form field is missing");
            }
            if (!(RequestValidator.validate(code))) {
                throw new IllegalArgumentException("Invalid currency code. Currency code must consist of three Latin letters!");
            }
            request.setAttribute("name", name);
            request.setAttribute("code", code);
            request.setAttribute("sign", sign);
        }
        filterChain.doFilter(request, servletResponse);
    }
}