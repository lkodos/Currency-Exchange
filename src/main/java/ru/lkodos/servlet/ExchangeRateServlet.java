package ru.lkodos.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/exchangeRates"}, name = "ExchangeRateServlet")
public class ExchangeRateServlet extends HttpServlet {

    private static final FullExchangeRateDao fullExchangeRateDao = FullExchangeRateDao.getInstance();
    private static final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<FullExchangeRate> allExchangeRates = fullExchangeRateDao.getAll();
        List<ExchangeRateDto> exchangeRateDto = MapperUtil.mapList(allExchangeRates, ExchangeRateDto.class);
        resp.setStatus(HttpServletResponse.SC_OK);
        ResponseSender.send(resp, exchangeRateDto);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCurrencyCode = (String) req.getAttribute("baseCurrencyCode");
        String targetCurrencyCode = (String) req.getAttribute("targetCurrencyCode");
        String rate = (String) req.getAttribute("rate");

        if (baseCurrencyCode.equals(targetCurrencyCode)) {
            throw new IllegalArgumentException("Base code matches target code");
        }

        Optional<Currency> baseCurrency = currencyDao.get(baseCurrencyCode);
        Optional<Currency> targetCurrency = currencyDao.get(targetCurrencyCode);

        if (baseCurrency.isEmpty() || targetCurrency.isEmpty()) {
            throw new CurrencyNotFoundException("One (or both) currencies from the currency pair do not exist in the database");
        }
        Integer baseCurrencyId = baseCurrency.get().getId();
        Integer targetCurrencyId = targetCurrency.get().getId();
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .baseCurrencyId(baseCurrencyId)
                .targetCurrencyId(targetCurrencyId)
                .rate(BigDecimal.valueOf(Double.parseDouble(rate)))
                .build();
        ExchangeRate newExchangeRate = exchangeRateDao.save(exchangeRate);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        ResponseSender.send(resp, newExchangeRate);
    }
}