package com.example.server.mapper;

import com.example.server.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.*;

@Mapper
public interface RateExMapper {

    int truncateTable();
    int insertRate(Rate rate);

    List<CurrencyJson> selectCurrency(String currency);

    List<Rate> getOnly(String curField);
    List<NationNameAll> getNationOnly(String curField);
    List<CurrencyJson> getNation();
    List<CurrencyJson> getNationName(String curField);
    List<NationNameAll> getNationNameAll();
    int fromSubmit(UserMoney userMoney);
    List<UserMoney> getUserMoneyAll(String userName);
    int setId(UserMoney userMoney);
    int delId(Long userId);
    List<UserMoney> getUserMoneyId(Long userId);
    int setUserMoneyId(Map<String, BigDecimal> map);

    List<UserMoney> getAll();

}
