package ru.lkodos.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import ru.lkodos.dto.CurrencyDto;
import ru.lkodos.dto.ExchangeRateDto;
import ru.lkodos.dto.ExchangeResultDto;
import ru.lkodos.entity.Currency;
import ru.lkodos.entity.FullExchangeRate;

public class MapperConfig {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CurrencyToCurrencyDtoMap();
        fullExchangeRateToExchangeRateDtoMap();
        fullExchangeRateToExchangeResultDtoMap();
    }

    private static void CurrencyToCurrencyDtoMap() {
        modelMapper.typeMap(Currency.class, CurrencyDto.class)
                .addMappings(mapper -> mapper.map(Currency::getFullName, CurrencyDto::setName));
    }

    private static void fullExchangeRateToExchangeRateDtoMap() {
        modelMapper.typeMap(FullExchangeRate.class, ExchangeRateDto.class).addMappings(mapper -> {
            mapper.map(FullExchangeRate::getBaseCurrencyId, (dest, v) -> dest.getBaseCurrency().setId((Integer) v));
            mapper.map(FullExchangeRate::getBaseCurrencyCode, (dest, v) -> dest.getBaseCurrency().setCode((String) v));
            mapper.map(FullExchangeRate::getBaseCurrencyName, (dest, v) -> dest.getBaseCurrency().setName((String) v));
            mapper.map(FullExchangeRate::getBaseCurrencySign, (dest, v) -> dest.getBaseCurrency().setSign((String) v));
            mapper.map(FullExchangeRate::getTargetCurrencyId, (dest, v) -> dest.getTargetCurrency().setId((Integer) v));
            mapper.map(FullExchangeRate::getTargetCurrencyCode, (dest, v) -> dest.getTargetCurrency().setCode((String) v));
            mapper.map(FullExchangeRate::getTargetCurrencyName, (dest, v) -> dest.getTargetCurrency().setName((String) v));
            mapper.map(FullExchangeRate::getTargetCurrencySign, (dest, v) -> dest.getTargetCurrency().setSign((String) v));
            mapper.map(FullExchangeRate::getRate, ExchangeRateDto::setRate);
        });
    }

    private static void fullExchangeRateToExchangeResultDtoMap() {
        modelMapper.typeMap(FullExchangeRate.class, ExchangeResultDto.class).addMappings(mapper -> {
            mapper.map(FullExchangeRate::getBaseCurrencyId, (dest, v) -> dest.getBaseCurrency().setId((Integer) v));
            mapper.map(FullExchangeRate::getBaseCurrencyCode, (dest, v) -> dest.getBaseCurrency().setCode((String) v));
            mapper.map(FullExchangeRate::getBaseCurrencyName, (dest, v) -> dest.getBaseCurrency().setName((String) v));
            mapper.map(FullExchangeRate::getBaseCurrencySign, (dest, v) -> dest.getBaseCurrency().setSign((String) v));
            mapper.map(FullExchangeRate::getTargetCurrencyId, (dest, v) -> dest.getTargetCurrency().setId((Integer) v));
            mapper.map(FullExchangeRate::getTargetCurrencyCode, (dest, v) -> dest.getTargetCurrency().setCode((String) v));
            mapper.map(FullExchangeRate::getTargetCurrencyName, (dest, v) -> dest.getTargetCurrency().setName((String) v));
            mapper.map(FullExchangeRate::getTargetCurrencySign, (dest, v) -> dest.getTargetCurrency().setSign((String) v));
            mapper.map(FullExchangeRate::getRate, ExchangeResultDto::setRate);
        });
    }

    public static ModelMapper getMapper() {
        return modelMapper;
    }
}