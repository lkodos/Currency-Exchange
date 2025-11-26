package ru.lkodos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// TODO: Какие аннотации lombok нужны здесь и на остальных entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullExchangeRate {

    private Integer id;
    private Integer baseCurrencyId;
    private String baseCurrencyCode;
    private String baseCurrencyName;
    private String baseCurrencySign;
    private Integer targetCurrencyId;
    private String targetCurrencyCode;
    private String targetCurrencyName;
    private String targetCurrencySign;
    private BigDecimal rate;
}