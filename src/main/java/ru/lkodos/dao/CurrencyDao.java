package ru.lkodos.dao;

import ru.lkodos.entity.Currency;
import ru.lkodos.entity.FullExchangeRate;
import ru.lkodos.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.*;

public class CurrencyDao implements Dao<String, Currency> {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String GET_ALL_SQL = "SELECT id, code, name, sign FROM currency";
    private static final String GET_BY_CODE_SQL = "SELECT id, code, name, sign FROM currency WHERE code = ?";
    private static final String SAVE_SQL = "INSERT INTO currency (code, name, sign) VALUES (?, ?, ?)";
    private static final String GET_ALL_EXCHANGE_RATES_SQL = """
                        SELECT e.id AS id,
                        e.base_currency_id AS base_id,
                        c.code AS base_code,
                        c.name AS base_name,
                        c.sign AS base_sign,
                        e.target_currency_id AS target_id,
                        c2.code AS target_code,
                        c2.name AS target_name,
                        c2.sign AS target_sign,
                        e.rate AS rate
                        FROM exchange_rates e
                        JOIN currency c ON c.id = e.base_currency_id
                        JOIN currency c2 ON c2.id = e.target_currency_id;
                        """;
    private static final String GET_EXCHANGE_RATE_SQL = """
                        SELECT e.id AS id,
                        e.base_currency_id AS base_id,
                        c.code AS base_code,
                        c.name AS base_name,
                        c.sign AS base_sign,
                        e.target_currency_id AS target_id,
                        c2.code AS target_code,
                        c2.name AS target_name,
                        c2.sign AS target_sign,
                        e.rate AS rate
                        FROM exchange_rates e
                        JOIN currency c ON c.id = e.base_currency_id
                        JOIN currency c2 ON c2.id = e.target_currency_id
                        WHERE base_id = ? AND target_id = ?
                        """;

    private CurrencyDao() {
    }

    @Override
    public Currency save(Currency entity) {
        try (var connection = ConnectionManager.getDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getSign());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            entity.setId(generatedKeys.getInt(1));
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Currency getByCode(String code) {
        try (var connection = ConnectionManager.getDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(GET_BY_CODE_SQL)) {

            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildCurrency(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Currency> getAll() {

        try (var connection = ConnectionManager.getDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {

            List<Currency> currencies = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FullExchangeRate> getFullExchangeRates() {
        try (var connection = ConnectionManager.getDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(GET_ALL_EXCHANGE_RATES_SQL)) {

            List<FullExchangeRate> fullExchangeRates = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fullExchangeRates.add(buildFullExchangeRate(resultSet));
            }
            return fullExchangeRates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    private Currency buildCurrency(ResultSet resultSet) {
        try {
            return new ru.lkodos.entity.Currency(
                    resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("sign")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private FullExchangeRate buildFullExchangeRate(ResultSet resultSet) {
        try {
            return FullExchangeRate.builder()
                    .id(resultSet.getInt("id"))
                    .baseId(resultSet.getInt("base_id"))
                    .baseCode(resultSet.getString("base_code"))
                    .baseName(resultSet.getString("base_name"))
                    .baseSign(resultSet.getString("base_sign"))
                    .targetId(resultSet.getInt("target_id"))
                    .targetCode(resultSet.getString("target_code"))
                    .targetName(resultSet.getString("target_name"))
                    .targetSign(resultSet.getString("target_sign"))
                    .rate(resultSet.getDouble("rate"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}