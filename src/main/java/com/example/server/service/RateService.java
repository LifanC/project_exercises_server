package com.example.server.service;

import com.example.server.model.CurrencyJson;
import com.example.server.model.NationNameAll;
import com.example.server.model.Rate;

import java.util.List;

public interface RateService{
    void saveRate (Rate rate);
    List<Rate> getOnly (String curField);
    List<NationNameAll> getNationOnly (String curField);
    List<CurrencyJson> getNation ();
    List<CurrencyJson> getNationName (String curField);
    List<NationNameAll> getNationNameAll ();

}
