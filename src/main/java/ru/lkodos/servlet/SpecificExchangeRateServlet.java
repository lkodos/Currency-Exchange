package ru.lkodos.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dto.ExchangeRateDto;
import ru.lkodos.service.ExchangeRateService;
import ru.lkodos.util.Sender;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/exchangeRate/*")
public class SpecificExchangeRateServlet extends HttpServlet {

    private static final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String code = req.getPathInfo().substring(1).toUpperCase();
        String baseId = code.substring(0, 3);
        String targetId = code.substring(3);

        Optional<ExchangeRateDto> exchangeRate = exchangeRateService.getExchangeRate(baseId, targetId);
        exchangeRate.ifPresent(exchangeRateDto -> Sender.send(resp, exchangeRateDto));
    }
}
