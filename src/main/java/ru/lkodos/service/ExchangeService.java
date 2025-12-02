package ru.lkodos.service;

import ru.lkodos.dao.FullExchangeRateDao;
import ru.lkodos.dto.CurrencyDto;
import ru.lkodos.dto.ExchangeDto;
import ru.lkodos.dto.ExchangeResultDto;
import ru.lkodos.entity.FullExchangeRate;
import ru.lkodos.exception.CurrencyNotFoundException;
import ru.lkodos.mapper.MapperUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeService {

    private static final ExchangeService INSTANCE = new ExchangeService();

    private static final FullExchangeRateDao fullExchangeRateDao = FullExchangeRateDao.getInstance();

    private ExchangeService() {
    }

    public ExchangeResultDto convert(ExchangeDto exchangeDto) {

        Optional<FullExchangeRate> fullExchangeRateOptional = getByCode(exchangeDto);
        if (fullExchangeRateOptional.isPresent()) {
            FullExchangeRate fullExchangeRate = fullExchangeRateOptional.get();
            BigDecimal convertedAmount = calculate(exchangeDto.getAmount(), fullExchangeRateOptional.get().getRate());
            return buidExchangeResultDto(exchangeDto, fullExchangeRate, convertedAmount);
        }
        else {
            fullExchangeRateOptional = getByReverseCode(exchangeDto);
            if (fullExchangeRateOptional.isPresent()) {
                FullExchangeRate fullExchangeRate = fullExchangeRateOptional.get();
                BigDecimal rate = BigDecimal.valueOf(1).divide(fullExchangeRate.getRate(),5, RoundingMode.HALF_EVEN);
                BigDecimal convertedAmount = calculate(exchangeDto.getAmount(), rate);
                return buidExchangeResultDto(exchangeDto, fullExchangeRate, convertedAmount);
            } else {
                Optional<FullExchangeRate> fullExchangeRateFrom = fullExchangeRateDao.getByCode("USD", exchangeDto.getFrom());
                Optional<FullExchangeRate> fullExchangeRateTo = fullExchangeRateDao.getByCode("USD", exchangeDto.getTo());
                if (fullExchangeRateFrom.isPresent() && fullExchangeRateTo.isPresent()) {
                    BigDecimal FromRate = fullExchangeRateFrom.get().getRate();
                    BigDecimal ToRate = fullExchangeRateTo.get().getRate();
                    BigDecimal rate = BigDecimal.valueOf(1).divide(FromRate, 5, RoundingMode.HALF_EVEN).multiply(ToRate);
                    BigDecimal convertedAmount = calculate(exchangeDto.getAmount(), rate);
                    CurrencyDto baseCurrencyDto = CurrencyDto.builder()
                            .id(fullExchangeRateFrom.get().getBaseCurrencyId())
                            .name(fullExchangeRateFrom.get().getBaseCurrencyName())
                            .code(fullExchangeRateFrom.get().getBaseCurrencyCode())
                            .sign(fullExchangeRateFrom.get().getBaseCurrencySign())
                            .build();
                    CurrencyDto targetCurrencyDto = CurrencyDto.builder()
                            .id(fullExchangeRateTo.get().getTargetCurrencyId())
                            .name(fullExchangeRateTo.get().getTargetCurrencyName())
                            .code(fullExchangeRateTo.get().getTargetCurrencyCode())
                            .sign(fullExchangeRateTo.get().getTargetCurrencySign())
                            .build();

                    return ExchangeResultDto.builder()
                            .baseCurrency(baseCurrencyDto)
                            .targetCurrency(targetCurrencyDto)
                            .rate(rate)
                            .amount(exchangeDto.getAmount())
                            .convertedAmount(convertedAmount)
                            .build();
                }
                else {
                    throw new CurrencyNotFoundException("One (or both) currencies from the currency pair do not exist in the database");
                }
            }
        }
    }


    private static ExchangeResultDto buidExchangeResultDto(ExchangeDto exchangeDto, FullExchangeRate fullExchangeRate, BigDecimal convertedAmount) {
        ExchangeResultDto exchangeResultDto = MapperUtil.map(fullExchangeRate, ExchangeResultDto.class);
        exchangeResultDto.setAmount(exchangeDto.getAmount());
        exchangeResultDto.setConvertedAmount(convertedAmount);
        return exchangeResultDto;
    }

    public BigDecimal calculate(Double amount, BigDecimal rate) {
        return BigDecimal.valueOf(amount).multiply(rate);
    }

    public Optional<FullExchangeRate> getByCode(ExchangeDto exchangeDto) {
        return fullExchangeRateDao.getByCode(exchangeDto.getFrom(), exchangeDto.getTo());
    }

    public Optional<FullExchangeRate> getByReverseCode(ExchangeDto exchangeDto) {
        return fullExchangeRateDao.getByCode(exchangeDto.getTo(), exchangeDto.getFrom());
    }

    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}