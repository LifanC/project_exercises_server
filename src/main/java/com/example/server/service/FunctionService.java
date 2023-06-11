package com.example.server.service;

import com.example.server.model.CustomerData;
import com.example.server.model.CustomerDataMoney;
import com.example.server.model.Ins_del;

import java.util.List;

public interface FunctionService {
    void register(CustomerData customerData) throws Exception;
    List<CustomerData> login(CustomerData customerData) throws Exception;
    List<CustomerDataMoney> edit(String userNameId,String passWord) throws Exception;
    List<CustomerDataMoney> function_money_userNameId(String userNameId);
    void save_cur(String userNameId,String cField,String cFieldMoney);
    List<CustomerDataMoney> putAddMoney(String setMoney, String curFieldMoney, String userNameId, String depositOrWithdrawMoney);
    List<Ins_del> ins_del(Ins_del ins_del);
    List<Ins_del> get_ins_del(Ins_del ins_del);
}
