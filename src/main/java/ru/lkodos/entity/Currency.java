package ru.lkodos.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Currency {

    Integer id;
    String code;
    String name;
    String symbol;
}