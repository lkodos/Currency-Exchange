package ru.lkodos.dao;

import ru.lkodos.entity.Currency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<String, Currency, BigDecimal> {

    @Override
    public List<Currency> getAll() {
        return List.of();
    }

    @Override
    public Optional<Currency> get(String key) {
        return Optional.empty();
    }

    @Override
    public Currency save(Currency currency) {
        return null;
    }

    @Override
    public Currency update(BigDecimal rate) {
        return null;
    }
}