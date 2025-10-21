package ru.lkodos.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.entity.Currency;
import ru.lkodos.util.Sender;

import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<Currency> currencies = currencyDao.getAll();
        Sender.send(resp, currencies);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        Currency currency = Currency.builder()
                .code(code)
                .name(name)
                .sign(sign)
                .build();
        Sender.send(resp, currencyDao.save(currency));
    }
}