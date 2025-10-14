package ru.lkodos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dao.CurrencyDao;

import java.io.IOException;

@WebServlet(value = "/currency/*", name = "SpecificCurrencyServlet")
public class SpecificCurrencyServlet extends HttpServlet {

    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getPathInfo());
    }
}
