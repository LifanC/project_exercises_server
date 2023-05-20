package com.example.server.mapper;

import com.example.server.model.CurrencyJson;
import com.example.server.model.Rate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RateExMapper {

    List<Rate> selectRate(String rateTime);
    int insertRate(Rate rate);

    int deleteRate(String rateTime);

    List<CurrencyJson> selectCurrency(String currency);

    List<Rate> getAll();
    List<Rate> getOnly(String curField);
    List<CurrencyJson> getNation();
    List<CurrencyJson> getNationName(String curField);
}
