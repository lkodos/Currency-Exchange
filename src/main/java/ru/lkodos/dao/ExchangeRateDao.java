package ru.lkodos.dao;

import ru.lkodos.db_util.ConnectionManager;
import ru.lkodos.entity.ExchangeRate;
import ru.lkodos.exception.CurrencyAlreadyExistsException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao implements Dao<String, ExchangeRate, BigDecimal> {

    private static final ExchangeRateDao INSTANCE = new ExchangeRateDao();

    private static final String SAVE = "INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate) VALUES (?, ?, ?)";

    private ExchangeRateDao() {
    }

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
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(SAVE)) {

            ps.setInt(1, exchangeRate.getBaseCurrencyId());
            ps.setInt(2, exchangeRate.getTargetCurrencyId());
            ps.setBigDecimal(3, exchangeRate.getRate());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            exchangeRate.setId(generatedKeys.getInt(1));
            return exchangeRate;
        } catch (SQLException e) {
            throw new CurrencyAlreadyExistsException("Exchange Rate already exists!", e);
        }
    }

    @Override
    public ExchangeRate update(BigDecimal rate) {
        return null;
    }

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }
}