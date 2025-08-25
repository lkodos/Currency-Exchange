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
        Optional<CurrencyDto> currencyDto = currency.map(
                currency1 -> new CurrencyDto(
                        currency1.getId(),
                        currency1.getCode(),
                        currency1.getFullName(),
                        currency1.getSign()
                ));


//        CurrencyDto currencyDto = currencyDao.getByCode(code)
//                .map(currency -> new CurrencyDto(
//                        currency.getId(),
//                        currency.getCode(),
//                        currency.getFullName(),
//                        currency.getSign()
//                ));


        return currencyDto;
    }

    public List<CurrencyDto> getAllCurrencies() {
        return currencyDao.getAll().stream()
                .map(currency -> new CurrencyDto(
                        currency.getId(),
                        currency.getCode(),
                        currency.getFullName(),
                        currency.getSign()
                ))
                .collect(Collectors.toList());
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}