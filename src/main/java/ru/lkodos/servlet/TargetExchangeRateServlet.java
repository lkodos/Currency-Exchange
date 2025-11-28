package ru.lkodos.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.dao.ExchangeRateDao;
import ru.lkodos.dao.FullExchangeRateDao;
import ru.lkodos.dto.ExchangeRateDto;
import ru.lkodos.entity.Currency;
import ru.lkodos.entity.ExchangeRate;
import ru.lkodos.entity.FullExchangeRate;
import ru.lkodos.exception.CurrencyNotFoundException;
import ru.lkodos.mapper.MapperUtil;
import ru.lkodos.servlet_util.ResponseSender;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = {"/exchangeRate/*"}, name = "TargetExchangeRateServlet")
public class TargetExchangeRateServlet extends HttpServlet {

    private static final FullExchangeRateDao fullExchangeRateDao = FullExchangeRateDao.getInstance();
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private static final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

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
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String baseCurrencyCode = (String) req.getAttribute("baseCurrencyCode");
        String targetCurrencyCode = (String) req.getAttribute("targetCurrencyCode");
        String body = (String) req.getAttribute("body");
        BigDecimal rate;
        try {
            rate = BigDecimal.valueOf(Double.parseDouble(body));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The field 'rate' value has an invalid format.");
        }

        Optional<Currency> baseCurrency = currencyDao.get(baseCurrencyCode);
        Optional<Currency> targetCurrency = currencyDao.get(targetCurrencyCode);

        if (baseCurrency.isPresent() && targetCurrency.isPresent()) {
            Integer baseCurrencyID = baseCurrency.get().getId();
            Integer targetCurrencyID = targetCurrency.get().getId();

            ExchangeRate exchangeRate = ExchangeRate.builder()
                    .baseCurrencyId(baseCurrencyID)
                    .targetCurrencyId(targetCurrencyID)
                    .rate(rate)
                    .build();

            exchangeRateDao.update(exchangeRate);
            Optional<FullExchangeRate> fullExchangeRate = fullExchangeRateDao.getByCode(baseCurrencyCode, targetCurrencyCode);
            if (fullExchangeRate.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                ExchangeRateDto exchangeRateDto = MapperUtil.map(fullExchangeRate.get(), ExchangeRateDto.class);
                ResponseSender.send(resp, exchangeRateDto);
            } else {
                throw new CurrencyNotFoundException("Exchange rate not found for the pair");
            }
        }
        else {
            throw new CurrencyNotFoundException("One (or both) currencies from the currency pair do not exist in the database");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }
}