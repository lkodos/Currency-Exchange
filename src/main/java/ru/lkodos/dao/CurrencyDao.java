package ru.lkodos.dao;

import ru.lkodos.db_util.ConnectionManager;
import ru.lkodos.entity.Currency;
import ru.lkodos.exception.CurrencyAlreadyExistsException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<String, Currency, BigDecimal> {

    private static final   CurrencyDao INSTANCE = new CurrencyDao();

    private static final String GET_ALL = "SELECT id, code, full_name, sign FROM currency";
    private static final String SAVE = "INSERT INTO currency (code, full_name, sign) VALUES (?, ?, ?)";
    private static final String GET_BY_CODE = "SELECT id, code, full_name, sign FROM currency WHERE code = ?";

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
    public Optional<Currency> get(String code) {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(GET_BY_CODE)) {

            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(buildCurrency(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Currency save(Currency currency) {
        try (var connection = ConnectionManager.getConnection();
             var ps = connection.prepareStatement(SAVE)) {

            ps.setString(1, currency.getCode());
            ps.setString(2, currency.getFullName());
            ps.setString(3, currency.getSign());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            currency.setId(generatedKeys.getInt(1));
            return currency;
        } catch (SQLException e) {
            throw new CurrencyAlreadyExistsException("Currency already exists!", e);
        }
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