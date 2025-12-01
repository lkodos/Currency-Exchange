package ru.lkodos.service;

import ru.lkodos.dao.ExchangeRateDao;
import ru.lkodos.dao.FullExchangeRateDao;
import ru.lkodos.dto.ExchangeDto;
import ru.lkodos.dto.ExchangeResultDto;
import ru.lkodos.entity.FullExchangeRate;
import ru.lkodos.mapper.MapperUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeService {

    private static final ExchangeService INSTANCE = new ExchangeService();

    private static final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
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
//                ExchangeResultDto exchangeResultDto = MapperUtil.map(fullExchangeRate.get(), ExchangeResultDto.class);
//                BigDecimal convertedAmount = calculate(exchangeDto.getAmount(), fullExchangeRate.get().getRate());
//                exchangeResultDto.setAmount(exchangeDto.getAmount());
//                exchangeResultDto.setConvertedAmount(BigDecimal.valueOf(1).divide(convertedAmount));
                return null;
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