package ru.lkodos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FullExchangeRate {

    private Integer id;
    private Integer baseId;
    private String baseCode;
    private String baseName;
    private String baseSign;
    private Integer targetId;
    private String targetCode;
    private String targetName;
    private String targetSign;
    private Double rate;
}