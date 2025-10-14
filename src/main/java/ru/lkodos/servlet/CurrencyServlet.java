package ru.lkodos.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.entity.Currency;
import ru.lkodos.util.SendAnswer;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Currency> allCurrencies = currencyDao.getAll();
        SendAnswer.sendAnswer(resp, allCurrencies);
    }
}