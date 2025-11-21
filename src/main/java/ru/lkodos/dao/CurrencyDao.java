package ru.lkodos.dao;

import ru.lkodos.db_util.ConnectionManager;
import ru.lkodos.entity.Currency;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<String, Currency, BigDecimal> {

    private static final   CurrencyDao INSTANCE = new CurrencyDao();

    private static final String GET_ALL = "SELECT id, code, full_name, sign FROM currency";

    private CurrencyDao() {
    }

    @Override
    public List<Currency> getAll() {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(GET_ALL)) {

            List<Currency> currencies = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                currencies.add(buildCurrency(rs));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private Currency buildCurrency(ResultSet rs) {
        try {
            return Currency.builder()
                    .id(rs.getInt("id"))
                    .code(rs.getString("code"))
                    .fullName(rs.getString("full_name"))
                    .sign(rs.getString("sign"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }
}