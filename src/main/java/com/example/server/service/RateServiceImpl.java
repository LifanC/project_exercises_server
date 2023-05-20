package com.example.server.service;

import com.example.server.mapper.RateExMapper;
import com.example.server.model.CurrencyJson;
import com.example.server.model.Rate;

import jakarta.annotation.Resource;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@Transactional
public class RateServiceImpl implements RateService {

    @Resource
    private RateExMapper rateExMapper;

    @Override
    public List<String> saveRate(Rate rate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        Map<?, ?> map = (Map<?, ?>) rate.getRates();


        List<Rate> data = rateExMapper.selectRate(rate.getDate());
        List<String> Y_ro_N = new ArrayList<>();
        if (data.size() == 0) {
            map.forEach((k, v) -> {
                rate.setCurLocal(rate.getBase());
                rate.setCurLocalMoney("1");
                rate.setCurField((String) k);
                rate.setCurFieldMoney(v.toString());
                rate.setCreateTime(formattedDate);
                List<CurrencyJson> dataJson = rateExMapper.selectCurrency((String) k);
                dataJson.forEach(e -> {
                    if(e.getCurrency().equals((String)k)){
                        rate.setCurNameJson(e.getCurrencyName());
                    }
                });
                rateExMapper.insertRate(rate);

            });
            Y_ro_N.add("true");
            Y_ro_N.add("更新成功");
            return Y_ro_N;
        } else {
            Y_ro_N.add("false");
            Y_ro_N.add("重複更新");
            return Y_ro_N;
        }
    }

    @Override
    public List<Rate> getAll() {
        return rateExMapper.getAll();
    }

    @Override
    public List<Rate> getOnly(String curField) {
        return rateExMapper.getOnly(curField);
    }

    @Override
    public List<CurrencyJson> getNation() {
        return rateExMapper.getNation();
    }

    @Override
    public List<CurrencyJson> getNationName(String curField) {
        return rateExMapper.getNationName(curField);
    }

}
