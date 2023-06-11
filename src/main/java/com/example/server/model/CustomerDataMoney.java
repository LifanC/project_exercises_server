package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataMoney{
    private Long function_money_id;
    private String userNameId;
    private String userName;
    private String cur_number;
    private Double ex_money;
    private String cur_field_money;
    private Double show_money;
    private String createTime;
    private String update_time;
    private String currency_name;
    private String currency_nation;

}
