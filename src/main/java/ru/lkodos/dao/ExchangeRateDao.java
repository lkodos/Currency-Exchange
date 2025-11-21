package ru.lkodos.dao;

import ru.lkodos.entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao implements Dao<String, ExchangeRate, BigDecimal> {

    @Override
    public List<ExchangeRate> getAll() {
        return List.of();
    }

    @Override
    public Optional<ExchangeRate> get(String key) {
        return Optional.empty();
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        return null;
    }

    @Override
    public ExchangeRate update(BigDecimal rate) {
        return null;
    }
}
