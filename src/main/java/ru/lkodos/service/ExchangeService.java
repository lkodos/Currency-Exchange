package ru.lkodos.service;

import ru.lkodos.dao.ExchangeRateDao;
import ru.lkodos.dao.FullExchangeRateDao;
import ru.lkodos.dto.ExchangeDto;
import ru.lkodos.entity.FullExchangeRate;

import java.util.Optional;

public class ExchangeService {

    private static final ExchangeService INSTANCE = new ExchangeService();

    private static final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private static final FullExchangeRateDao fullExchangeRateDao = FullExchangeRateDao.getInstance();

    private ExchangeService() {
    }

    public Optional<FullExchangeRate> getByCode(ExchangeDto exchangeDto) {
        return fullExchangeRateDao.getByCode(exchangeDto.getFrom(), exchangeDto.getTo());
    }

    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}