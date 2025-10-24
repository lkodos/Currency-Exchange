package ru.lkodos.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.service.ExchangeRateService;
import ru.lkodos.dto.ExchangeRateDto;
import ru.lkodos.util.Sender;

import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRateServlet extends HttpServlet {

    private static final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        List<ExchangeRateDto> rates = exchangeRateService.getAllExchangeRates();
        Sender.send(resp, rates);
    }
}
