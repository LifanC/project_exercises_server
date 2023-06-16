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
    String findPassword(String userName) throws Exception;
    List<Ins_del> findDatePicker(String DatePickerStart, String DatePickerEnd);
    List<Ins_del> findDatePicker1(String DatePickerStart, String DatePickerEnd);
    Integer total_amount(String DatePickerStart, String DatePickerEnd);
    List<Ins_del> findIns_del(String DatePickerStart, String DatePickerEnd);
    void setTableData(Long ins_del_data_id,String expense_and_income_number,Integer inputMoney,Integer setInputMoney,String calendarDetails);
    void del(Ins_del ins_del);
}
