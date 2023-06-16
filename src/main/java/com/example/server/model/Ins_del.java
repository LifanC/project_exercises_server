package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ins_del {
    private Long ins_del_id;
    private Long ins_del_data_id;
    private String calendarDetails;
    private String ex;
    private String insDel;
    private Integer expense;
    private Integer income;
    private String expense_and_income_number;
    private Integer totleMoney;
    private Integer inputMoney;
    private String details;
    private String expense_and_income_name;
    private Integer setInputMoney;
}
