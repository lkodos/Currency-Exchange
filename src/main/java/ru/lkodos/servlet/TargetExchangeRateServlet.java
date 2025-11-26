package ru.lkodos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.lkodos.dao.FullExchangeRateDao;
import ru.lkodos.dto.ExchangeRateDto;
import ru.lkodos.entity.FullExchangeRate;
import ru.lkodos.exception.CurrencyNotFoundException;
import ru.lkodos.mapper.MapperUtil;
import ru.lkodos.servlet_util.ResponseSender;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/exchangeRate/*"}, name = "TargetExchangeRateServlet")
public class TargetExchangeRateServlet extends HttpServlet {

    private static final FullExchangeRateDao fullExchangeRateDao = FullExchangeRateDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCurrencyCode = (String) req.getAttribute("baseCurrencyCode");
        String targetCurrencyCode = (String) req.getAttribute("targetCurrencyCode");

        Optional<FullExchangeRate> exchangeRate = fullExchangeRateDao.getByCode(baseCurrencyCode, targetCurrencyCode);
        if (exchangeRate.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            ExchangeRateDto exchangeRateDto = MapperUtil.map(exchangeRate.get(), ExchangeRateDto.class);
            ResponseSender.send(resp, exchangeRateDto);
        } else {
            throw new CurrencyNotFoundException("Exchange rate not found for the pair");
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPatch(req, resp);
    }
}