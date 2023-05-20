package com.example.server.service;

import com.example.server.model.CurrencyJson;
import com.example.server.model.Rate;

import java.util.List;

public interface RateService{
    List<String> saveRate (Rate rate);
    List<Rate> getAll ();
    List<Rate> getOnly (String curField);
    List<CurrencyJson> getNation ();
    List<CurrencyJson> getNationName (String curField);

}
