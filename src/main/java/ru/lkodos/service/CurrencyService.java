package ru.lkodos.service;

import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.dto.CurrencyDto;
import ru.lkodos.entity.Currency;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyService {

    private static final CurrencyService INSTANCE = new CurrencyService();

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private CurrencyService() {
    }

    public Optional<CurrencyDto> getCurrencyByCode(String code) {

        Optional<Currency> currency = currencyDao.getByCode(code);
        return currency.map(this::buildCurrencyDto);
    }

    public List<CurrencyDto> getAllCurrencies() {
        return currencyDao.getAll().stream()
                .map(this::buildCurrencyDto)
                .collect(Collectors.toList());
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    private CurrencyDto buildCurrencyDto(Currency currency) {
        return new CurrencyDto(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }
}