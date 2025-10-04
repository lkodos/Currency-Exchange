package ru.lkodos.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dto.CurrencyDto;
import ru.lkodos.service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/currencies/*")
public class SpecificCurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = (String) req.getAttribute("code");
        Optional<CurrencyDto> currencyByCode = currencyService.getCurrencyByCode(code);
        String json = new Gson().toJson(currencyByCode);
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(json);
        }
    }
}