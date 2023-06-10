package com.example.server.service;

import com.example.server.model.CustomerData;
import com.example.server.model.CustomerDataMoney;

import java.util.List;

public interface FunctionService {
    void register(CustomerData customerData);
    List<CustomerData> login(CustomerData customerData);
    void edit(String userNameId,String passWord);
    List<CustomerDataMoney> function_money_userNameId(String userNameId);
    void save_cur(String userNameId,String cField,String cFieldMoney);
    List<CustomerDataMoney> putAddMoney(String setMoney, String curFieldMoney, String userNameId, String depositOrWithdrawMoney);
}
