package ru.lkodos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dto.ExchangeDto;

import java.io.IOException;

@WebServlet(urlPatterns = {"/exchange"}, name = "ExchangeServlet")
public class ExchangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = (String) req.getAttribute("from");
        String to = (String) req.getAttribute("to");
        int amount;

        try {
            amount = Integer.parseInt(req.getParameter("amount"));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal amount parameter");
        }

        ExchangeDto exchangeDto = ExchangeDto.builder()
                .from(from)
                .to(to)
                .amount(amount)
                .build();
    }
}