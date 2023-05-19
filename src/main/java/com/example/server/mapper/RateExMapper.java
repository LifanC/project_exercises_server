package com.example.server.mapper;

import com.example.server.model.Rate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RateExMapper {

    List<Rate> selectRate(String rateTime);
    int insertRate(Rate rate);

    int deleteRate(String rateTime);
}
