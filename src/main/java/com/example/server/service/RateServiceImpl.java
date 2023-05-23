package com.example.server.service;

import com.example.server.mapper.RateExMapper;
import com.example.server.model.CurrencyJson;
import com.example.server.model.NationNameAll;
import com.example.server.model.Rate;

import com.example.server.model.UserMoney;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@Transactional
public class RateServiceImpl implements RateService{

    @Resource
    private RateExMapper rateExMapper;

    public String timeFormatter(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public String formatteryyyyMMdd(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    @Override
    public void saveRate(Rate rate) {
        Map<?, ?> map = (Map<?, ?>) rate.getRates();

        List<Rate> data = rateExMapper.selectRate(rate.getDate());
        if (data.size() == 0) {
            map.forEach((k, v) -> {
                rate.setCurLocal(rate.getBase());
                rate.setCurLocalMoney("1");
                rate.setCurField((String) k);
                rate.setCurFieldMoney(v.toString());
                rate.setCreateTime(timeFormatter());
                List<CurrencyJson> dataJson = rateExMapper.selectCurrency((String) k);
                dataJson.forEach(e -> {
                    if(e.getCurrency().equals((String)k)){
                        rate.setCurNameJson(e.getCurrencyName());
                    }
                });
                rateExMapper.insertRate(rate);

            });
        }
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
        BigDecimal v = new BigDecimal(String.valueOf(userMoney.getExMoney()));
        BigDecimal x = new BigDecimal(userMoney.getCurFieldMoney());
        userMoney.setShowMoney(v.multiply(x));
        rateExMapper.fromSubmit(userMoney);
        List<UserMoney> data = rateExMapper.getUserMoneyAll(userMoney.getUserName());
        data.forEach(e->{
            userMoney.setUserNameId(formatteryyyyMMdd()+e.getUserId());
            rateExMapper.setId(userMoney);
        });
        List<UserMoney> showData = rateExMapper.getUserMoneyAll(userMoney.getUserName());
        return showData;
    }

    @Override
    public List<UserMoney> getUserMoney(UserMoney userMoney) {
        return rateExMapper.getUserMoneyAll(userMoney.getUserName());
    }

    @Override
    public List<UserMoney> delId(Long userId,String userName) {
        rateExMapper.delId(userId);
        return rateExMapper.getUserMoneyAll(userName);
    }

    @Override
    public List<UserMoney> putAddMoney(String setMoney, String curFieldMoney, Long userId,String userName) {
        List<UserMoney> data = rateExMapper.getUserMoneyId(userId);
        data.forEach(z->{
            BigDecimal aNew = new BigDecimal(setMoney);
            BigDecimal bOld = new BigDecimal(String.valueOf(z.getExMoney()));
            //錢(新)+錢(舊)
            BigDecimal aNewAddbOld = aNew.add(bOld);
            BigDecimal c = new BigDecimal(curFieldMoney);
            BigDecimal d = aNew.multiply(c);
            BigDecimal e = new BigDecimal(String.valueOf(z.getShowMoney()));
            //錢(*匯率)+錢(原本匯率)
            BigDecimal dAdde = d.add(e);
            Map map = new HashMap<>();
            map.put("exMoney",aNewAddbOld);
            map.put("showMoney",dAdde);
            map.put("userId",userId);
            rateExMapper.setUserMoneyId(map);
        });
        return rateExMapper.getUserMoneyAll(userName);
    }


}
