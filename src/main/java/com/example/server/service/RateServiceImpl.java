package com.example.server.service;

import com.example.server.mapper.RateExMapper;
import com.example.server.model.CurrencyJson;
import com.example.server.model.NationNameAll;
import com.example.server.model.Rate;

import com.example.server.model.UserMoney;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class RateServiceImpl implements RateService {

    @Resource
    private RateExMapper rateExMapper;

    public String timeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
    public List<NationNameAll> getNationOnly(String curField) {
        return rateExMapper.getNationOnly(curField);
    }

    @Override
    public List<CurrencyJson> getNation() {
        return rateExMapper.getNation();
    }

    @Override
    public List<CurrencyJson> getNationName(String curField) {
        return rateExMapper.getNationName(curField);
    }

    @Override
    public List<NationNameAll> getNationNameAll() {
        return rateExMapper.getNationNameAll();
    }

    @Override
    public List<UserMoney> fromSubmit(UserMoney userMoney) {
        userMoney.setCreateTime(timeFormatter());
        rateExMapper.fromSubmit(userMoney);
        userMoney.setUserNameId(formatteryyyyMMdd() + userMoney.getUserId());
        rateExMapper.setId(userMoney);
        List<UserMoney> showData = rateExMapper.getUserMoneyAll(userMoney.getUserName());
        return showData;
    }

    @Override
    public List<UserMoney> getUserMoney(UserMoney userMoney) {
        return rateExMapper.getUserMoneyAll(userMoney.getUserName());
    }

    @Override
    public List<UserMoney> delId(Long userId, String userName) {
        rateExMapper.delId(userId);
        return rateExMapper.getUserMoneyAll(userName);
    }

    @Override
    public List<UserMoney> putAddMoney(
            String setMoney,
            String curFieldMoney,
            Long userId,
            String userName,
            String depositOrWithdrawMoney
    ) {
        List<UserMoney> data = rateExMapper.getUserMoneyId(userId);
        Map map = new HashMap<>();
        if ("depositMoney".equals(depositOrWithdrawMoney)) {
            data.forEach(z -> {
                BigDecimal depositNew = new BigDecimal(setMoney);
                BigDecimal depositOld = new BigDecimal(String.valueOf(z.getExMoney()));
                //錢(新)+錢(舊)
                BigDecimal depositNew_Add_depositOld = depositNew.add(depositOld);
                BigDecimal a = new BigDecimal(curFieldMoney);
                BigDecimal b = depositNew.multiply(a);
                BigDecimal c = new BigDecimal(String.valueOf(z.getShowMoney()));
                //錢(*匯率)+錢(原本匯率)
                BigDecimal dAdde = b.add(c).setScale(0, RoundingMode.HALF_UP);
                map.put("exMoney", depositNew_Add_depositOld);
                map.put("showMoney", dAdde);
                map.put("userId", userId);
            });
        } else if ("withdrawMoney".equals(depositOrWithdrawMoney)) {
            data.forEach(y -> {
                BigDecimal withdrawNew = new BigDecimal(setMoney);
                BigDecimal withdrawOld = new BigDecimal(String.valueOf(y.getExMoney()));
                //錢(舊)-錢(新)
                BigDecimal withdrawOld_Subtract_withdrawNew = withdrawOld.subtract(withdrawNew);
                BigDecimal a = new BigDecimal(curFieldMoney);
                BigDecimal b = withdrawNew.multiply(a);
                BigDecimal c = new BigDecimal(String.valueOf(y.getShowMoney()));
                //錢(原本匯率)-錢(*匯率)
                BigDecimal dSubtract = c.subtract(b).setScale(0, RoundingMode.HALF_UP);
                map.put("exMoney", withdrawOld_Subtract_withdrawNew);
                map.put("showMoney", dSubtract);
                map.put("userId", userId);
            });
        }
        rateExMapper.setUserMoneyId(map);
        return rateExMapper.getUserMoneyAll(userName);
    }


}
