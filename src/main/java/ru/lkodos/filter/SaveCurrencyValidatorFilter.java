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
            String name;
            String code;
            String sign;
            try {
                name = request.getParameter("name");
                code = request.getParameter("code");
                sign = request.getParameter("sign");
            } catch (Exception e) {
                throw new IllegalArgumentException("Required form field is missing");
            }

            if (name == null || code == null || sign == null || name.isEmpty() || code.isEmpty() || sign.isEmpty()) {
                throw new IllegalArgumentException("Required form field is missing");
            }

            if (!(RequestValidator.validate(code))) {
                throw new IllegalArgumentException("Invalid currency code. Сurrency code must consist of three Latin letters!");
            }
            request.setAttribute("name", name);
            request.setAttribute("code", code);
            request.setAttribute("sign", sign);
        }
        filterChain.doFilter(request, servletResponse);
    }
}
