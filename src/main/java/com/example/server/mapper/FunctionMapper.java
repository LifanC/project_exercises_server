package com.example.server.mapper;

import com.example.server.model.CustomerData;
import com.example.server.model.CustomerDataMoney;
import com.example.server.model.Ins_del;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FunctionMapper {
    int countUserName(CustomerData customerData);
    List<CustomerData> userName(CustomerData customerData);
    int register(CustomerData customerData);
    int registerMoney(CustomerDataMoney customerDataMoney);
    List<CustomerData> login(CustomerData customerData);
    void edit(String userNameId, String passWord, String formatteryyyyMMdd);
    List<CustomerDataMoney> userId(String userNameId);
    void save_cur(String userNameId, String cField, String cFieldMoney);
    List<CustomerDataMoney> userNameId(String userNameId);
    int setNameId(Map map);
    int add(Ins_del ins_del);
    int findAdd(Ins_del ins_del);
    int set(Ins_del ins_del);
    List<Ins_del> sel(Ins_del ins_del);
}
