package com.example.server.service;

import com.example.server.mapper.RateExMapper;
import com.example.server.model.CurrencyJson;
import com.example.server.model.Rate;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class RateServiceImpl implements RateService {

    @Resource
    private RateExMapper rateExMapper;

    public String timeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public String formatteryyyyMMdd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    @Override
    public void saveRate(Rate rate) {
        Map<?, ?> map = (Map<?, ?>) rate.getRates();
        rateExMapper.truncateTable();
        map.forEach((k, v) -> {
            rate.setCurLocal(rate.getBase());
            rate.setCurLocalMoney("1");
            rate.setCurField((String) k);
            rate.setCurFieldMoney(v.toString());
            rate.setCreateTime(timeFormatter());
            List<CurrencyJson> dataJson = rateExMapper.selectCurrency((String) k);
            dataJson.forEach(e -> {
                if (e.getCurrency().equals((String) k)) {
                    rate.setCurNameJson(e.getCurrencyName());
                }
            });
            rateExMapper.insertRate(rate);
        });
    }


    @Override
    public List<Rate> getOnly(String curField) {
        return rateExMapper.getOnly(curField);
    }

    @Override
    public List<CurrencyJson> getNation() {
        return rateExMapper.getNation();
    }


}
