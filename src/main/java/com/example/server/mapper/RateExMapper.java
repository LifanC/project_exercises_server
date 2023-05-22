package com.example.server.mapper;

import com.example.server.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface RateExMapper {

    List<Rate> selectRate(String rateTime);
    int insertRate(Rate rate);

    int deleteRate(String rateTime);

    List<CurrencyJson> selectCurrency(String currency);

    List<Rate> getOnly(String curField);
    List<NationNameAll> getNationOnly(String curField);
    List<CurrencyJson> getNation();
    List<CurrencyJson> getNationName(String curField);
    List<NationNameAll> getNationNameAll();
    int fromSubmit(UserMoney userMoney);
    List<UserMoney> getUserMoneyAll(String userName);
    int setId(UserMoney userMoney);
}
