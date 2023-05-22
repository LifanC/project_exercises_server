package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserMoney extends CreateTime{
    private Long userId;
    private String userNameId;
    private String userName, nation, rateName;
    private BigDecimal exMoney;
    private String curFieldMoney;
    private BigDecimal showMoney;
}
