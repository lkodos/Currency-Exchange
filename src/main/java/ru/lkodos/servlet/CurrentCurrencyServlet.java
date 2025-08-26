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
import java.util.Optional;

@WebServlet("/currency/*")
public class CurrentCurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String currencyCode = req.getPathInfo().substring(1).toUpperCase();

        Optional<CurrencyDto> currencyByCode = currencyService.getCurrencyByCode(currencyCode);
        if (currencyCode.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            String message = "Код валюты отсутствует в адресе";
            String currencies = new Gson().toJson("message: " + message);
            try (var writer = resp.getWriter()) {
                writer.write(currencies);
                writer.flush();
            }
        } else if (currencyByCode.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            String message = "Валюта не найдена";
            String currencies = new Gson().toJson("message: " + message);
            try (var writer = resp.getWriter()) {
                writer.write(currencies);
                writer.flush();
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            String currencies = new Gson().toJson(currencyByCode);
            try (var writer = resp.getWriter()) {
                writer.write(currencies);
                writer.flush();
            }
        }
    }
}