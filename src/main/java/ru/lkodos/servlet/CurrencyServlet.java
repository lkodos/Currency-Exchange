package ru.lkodos.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dto.CurrencyDto;
import ru.lkodos.service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CurrencyDto> allCurrencies = currencyService.getAllCurrencies();
        String json = new Gson().toJson(allCurrencies);
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(json);
        }
    }
}