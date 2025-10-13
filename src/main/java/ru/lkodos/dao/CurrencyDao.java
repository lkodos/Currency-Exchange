package ru.lkodos.dao;

import ru.lkodos.entity.Currency;
import ru.lkodos.exception.DbAccessException;
import ru.lkodos.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao implements Dao<Integer, Currency> {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String GET_ALL_SQL = "SELECT id, code, name, sign FROM currency";

    private CurrencyDao() {
    }

    @Override
    public List<Currency> getAll() {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(GET_ALL_SQL)) {

            List<Currency> currencies = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                currencies.add(buildCurrency(rs));
            }
            return currencies;
        } catch (SQLException e) {
            throw new DbAccessException("Failed to complete request", e);
        }
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    private Currency buildCurrency(ResultSet resultSet) {
        try {
            return new Currency(
                    resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("sign")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}