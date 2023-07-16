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
    List<CurrencyJson> getNation();

}
