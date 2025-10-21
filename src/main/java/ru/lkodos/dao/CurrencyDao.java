package ru.lkodos.dao;

import ru.lkodos.entity.Currency;
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

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    private ru.lkodos.entity.Currency buildCurrency(ResultSet resultSet) {
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
}