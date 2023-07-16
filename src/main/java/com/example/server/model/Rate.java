package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rate {
    private Long rateId;
    private String base;
    private String date;
    private Object rates;
    private String curLocal;
    private String curLocalMoney;
    private String curField;
    private String curFieldMoney;
    private Object curNameJson;
    private String currencyNation;
    private String createTime;
}
