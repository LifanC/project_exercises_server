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

}
