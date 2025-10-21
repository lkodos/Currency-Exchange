package ru.lkodos.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.entity.Currency;
import ru.lkodos.util.Sender;

import java.nio.charset.StandardCharsets;

@WebServlet("/currency/*")
public class SpecificCurrencyServlet extends HttpServlet {

    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String code = req.getPathInfo().substring(1).toUpperCase();
        Currency currency = currencyDao.getByCode(code);
        Sender.send(resp, currency);
    }
}
