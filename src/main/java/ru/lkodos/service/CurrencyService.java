package ru.lkodos.service;

import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.dto.CurrencyDto;
import ru.lkodos.entity.Currency;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class CurrencyService {

    private static final CurrencyService INSTANCE = new CurrencyService();

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private CurrencyService() {
    }

    public Optional<CurrencyDto> getCurrencyByCode(String code) {
        Optional<Currency> specificCurrency = currencyDao.getByCode(code);
        return specificCurrency.map(this::buildCurrencyDto);
    }

    public List<CurrencyDto> getAllCurrencies() {
        return currencyDao.getAll().stream()
                .map(this::buildCurrencyDto)
                .collect(toList());
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    private CurrencyDto buildCurrencyDto(Currency currency) {
        return new CurrencyDto(
                currency.getId(),
                currency.getCode(),
                currency.getName(),
                currency.getSign()
        );
    }
}