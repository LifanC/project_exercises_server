package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyJson {
    private String currencyId;
    private String currency;
    private String currencyName;
    private String currencyNation;
}
