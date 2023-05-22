package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NationNameAll {
    private String curField;
    private String currency;
    private String currencyName;
    private String currencyNation;
    private String currencyNationArray;

    public String getCurrencyNationArray() {
        String v1 = currencyNation.replace('[', ' ').replace(']', ' ');
        String v2 = v1.replace('"', ' ').replace('"', ' ');
        return v2;
    }
}
