package ru.lkodos.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dto.CurrencyDto;
import ru.lkodos.service.CurrencyService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        List<CurrencyDto> allCurrencies = currencyService.getAllCurrencies();
        String currencies = new Gson().toJson(allCurrencies);
        try (var writer = resp.getWriter()) {
            writer.write(currencies);
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Optional<CurrencyDto> currencyDto = currencyService.insertCurrency();
        if (currencyDto.isPresent()) {
            String currencies = new Gson().toJson(currencyDto);
            try (var writer = resp.getWriter()) {
                writer.write(currencies);
                writer.flush();
            }
        } else {
            String currencies = new Gson().toJson("message: Валюта уже добавлена");
            try (var writer = resp.getWriter()) {
                writer.write(currencies);
                writer.flush();
            }
        }

    }
}