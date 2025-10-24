package ru.lkodos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.lkodos.entity.Currency;

@Data
@AllArgsConstructor
@Builder
public class ExchangeRateDto {

    private Integer id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private Double rate;
}