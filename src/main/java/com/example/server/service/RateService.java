package com.example.server.service;

import com.example.server.model.*;

import java.util.List;

public interface RateService{
    void saveRate (Rate rate);
    List<Rate> getOnly (String curField);
    List<CurrencyJson> getNation ();

}
