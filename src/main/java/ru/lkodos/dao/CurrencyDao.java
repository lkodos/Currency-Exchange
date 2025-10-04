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

    private static final String GET_ALL_SQL = """
            SELECT id, name, code, sign
            FROM currency
            """;

    private static final String GET_BY_CODE_SQL = """
            SELECT id, name, code, sign
            FROM currency
            WHERE code = ?
            """;

    private CurrencyDao() {
    }

    public Optional<Currency> getByCode(String code) {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(GET_BY_CODE_SQL)) {

            ps.setObject(1, code);
            var rs = ps.executeQuery();
            Optional<Currency> specificCurrency;
            if (rs.next()) {
                specificCurrency = Optional.of(builCurrency(rs));
            } else {
                specificCurrency = Optional.empty();
            }
            return specificCurrency;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Currency> getAll() {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(GET_ALL_SQL)) {

            List<Currency> currencies = new ArrayList<>();
            var rs = ps.executeQuery();
            while (rs.next()) {
                currencies.add(builCurrency(rs));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    private Currency builCurrency(ResultSet rs) {
        try {
            return new Currency(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("sign")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}