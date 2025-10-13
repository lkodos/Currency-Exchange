package ru.lkodos.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.entity.Currency;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<Currency> allCurrencies = currencyDao.getAll();
        String json = new Gson().toJson(allCurrencies);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
        }
    }
}