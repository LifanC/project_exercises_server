package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

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
    private String createTime;
}
