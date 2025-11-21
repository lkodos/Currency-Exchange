package ru.lkodos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRate {

    Integer id;
    Integer baseCurrencyId;
    Integer targetCurrencyId;
    BigDecimal rate;
}