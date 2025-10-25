package ru.lkodos.service;

import ru.lkodos.dao.CurrencyDao;
import ru.lkodos.dto.ExchangeRateDto;
import ru.lkodos.entity.Currency;
import ru.lkodos.entity.FullExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {

    private static final ExchangeRateService INSTANCE = new ExchangeRateService();

    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private ExchangeRateService() {
    }

    public List<ExchangeRateDto> getAllExchangeRates() {
        List<FullExchangeRate> fullExchangeRates = currencyDao.getFullExchangeRates();
        return fullExchangeRates.stream()
                .map(value -> ExchangeRateDto.builder()
                        .id(value.getId())
                        .baseCurrency(
                                Currency.builder()
                                        .id(value.getBaseId())
                                        .code(value.getBaseCode())
                                        .name(value.getBaseName())
                                        .sign(value.getBaseSign())
                                        .build())
                        .targetCurrency(
                                Currency.builder()
                                        .id(value.getTargetId())
                                        .code(value.getTargetCode())
                                        .name(value.getTargetName())
                                        .sign(value.getTargetSign())
                                        .build())
                        .rate(value.getRate())
                        .build())
                .toList();
    }

    public Optional<ExchangeRateDto> getExchangeRate(String base_code, String target_code) {
        Optional<FullExchangeRate> exchangeRate = currencyDao.getExchangeRate(base_code, target_code);
        if(exchangeRate.isPresent()) {
            return exchangeRate.map(value -> ExchangeRateDto.builder()
                    .id(value.getId())
                    .baseCurrency(
                            Currency.builder()
                                    .id(value.getBaseId())
                                    .code(value.getBaseCode())
                                    .name(value.getBaseName())
                                    .sign(value.getBaseSign())
                                    .build())
                    .targetCurrency(
                            Currency.builder()
                                    .id(value.getTargetId())
                                    .code(value.getTargetCode())
                                    .name(value.getTargetName())
                                    .sign(value.getTargetSign())
                                    .build())
                    .rate(value.getRate())
                    .build());
        }
        return Optional.empty();
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}