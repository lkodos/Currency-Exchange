package ru.lkodos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateDto {

    private Integer id;
    private CurrencyDto baseCurrency;
    private CurrencyDto targetCurrency;
    BigDecimal rate;
}