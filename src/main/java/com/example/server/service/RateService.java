package com.example.server.service;

import com.example.server.model.*;

import java.util.List;

public interface RateService{
    void saveRate (Rate rate);
    List<Rate> getOnly (String curField);
    List<NationNameAll> getNationOnly (String curField);
    List<CurrencyJson> getNation ();
    List<CurrencyJson> getNationName (String curField);
    List<NationNameAll> getNationNameAll ();
    List<UserMoney> fromSubmit(UserMoney userMoney);
    List<UserMoney> getUserMoney(UserMoney userMoney);
    List<UserMoney> delId(Long userId,String userName);
    List<UserMoney> putAddMoney(String setMoney,String curFieldMoney,Long userId,String userName,String depositOrWithdrawMoney);

}
