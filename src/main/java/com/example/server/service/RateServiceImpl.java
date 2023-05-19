package com.example.server.service;

import com.example.server.mapper.RateExMapper;
import com.example.server.model.Rate;
import jakarta.annotation.Resource;
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
        LocalDateTime date = LocalDateTime .now();
        String formattedDate = date.format(formatter);
        Map<?, ?> map = (Map<?, ?>) rate.getRates();
        List<Rate> data = rateExMapper.selectRate(rate.getDate());
        List<String> x = new ArrayList<>();
        if(data.size() == 0){
            map.forEach((k, v) -> {
                rate.setCurLocal(rate.getBase());
                rate.setCurLocalMoney("1");
                rate.setCurField((String) k);
                rate.setCurFieldMoney(v.toString());
                rate.setCreateTime(formattedDate);
            rateExMapper.insertRate(rate);
            });
            x.add("true");
            x.add("更新成功");
            return x;
        }else{
            x.add("false");
            x.add("重複更新");
            return x;
        }
    }
}
