package ru.lkodos.dao;

import ru.lkodos.entity.Currency;
import ru.lkodos.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao implements Dao<Integer, Currency> {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String GET_ALL_SQL = """
            SELECT id, name, code, sign
            FROM currency
            """;

    private CurrencyDao() {
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