package ru.lkodos.dao;

import ru.lkodos.entity.Currency;
import ru.lkodos.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<Integer, Currency> {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String GET_ALL = """
            SELECT
                id,
                code,
                full_name,
                sign
            FROM currency
            """;
    private static final String GET_BY_CODE = """
            SELECT
                id,
                code,
                full_name,
                sign
            FROM currency
            WHERE code = ?
            """;

    private CurrencyDao() {
    }

    public Optional<Currency> getByCode(String code) {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(GET_BY_CODE)) {
            ps.setObject(1, code);
            var rs = ps.executeQuery();
            Optional<Currency> currency;
            if (rs.next()) {
                currency = Optional.of(buildCurrency(rs));
            } else {
                currency = Optional.empty();
            }
            return currency;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Currency> getAll() {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(GET_ALL)) {
            List<Currency> currencies = new ArrayList<>();
            var rs = ps.executeQuery();
            while (rs.next()) {
                currencies.add(buildCurrency(rs));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    private Currency buildCurrency(ResultSet resultSet) {
        try {
            return new Currency(
                    resultSet.getObject("id", Integer.class),
                    resultSet.getObject("code", String.class),
                    resultSet.getObject("full_name", String.class),
                    resultSet.getObject("sign", String.class)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}