package com.example.server.mapper;

import com.example.server.model.CustomerData;
import com.example.server.model.CustomerDataMoney;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FunctionMapper {
    int count(CustomerData customerData);
    int register(CustomerData customerData);
    int registerMoney(CustomerData customerData);
    List<CustomerData> login(CustomerData customerData);
    void edit(String userNameId, String passWord);
    List<CustomerDataMoney> userId(String userNameId);
    void save_cur(String userNameId, String cField, String cFieldMoney);
    List<CustomerDataMoney> userNameId(String userNameId);
    int setNameId(Map map);
}
