package ru.lkodos.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dto.ExchangeDto;
import ru.lkodos.dto.ExchangeResultDto;
import ru.lkodos.service.ExchangeService;
import ru.lkodos.servlet_util.ResponseSender;

import java.io.IOException;

@WebServlet(urlPatterns = {"/exchange"}, name = "ExchangeServlet")
public class ExchangeServlet extends HttpServlet {

    private static final ExchangeService exchangeService = ExchangeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from = (String) req.getAttribute("from");
        String to = (String) req.getAttribute("to");
        Double amount;

        try {
            amount = Double.parseDouble(req.getParameter("amount"));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal amount parameter");
        }

        ExchangeDto exchangeDto = ExchangeDto.builder()
                .from(from)
                .to(to)
                .amount(amount)
                .build();

        ExchangeResultDto exchangeResultDto = exchangeService.convert(exchangeDto);
        resp.setStatus(HttpServletResponse.SC_OK);
        ResponseSender.send(resp, exchangeResultDto);
    }
}