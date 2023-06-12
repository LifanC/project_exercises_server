package com.example.server.controller;

import com.example.server.model.*;
import com.example.server.service.RateService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@Transactional
@CrossOrigin(origins = "*")
@RequestMapping("/rate")
public class RateController {

    @Resource
    private RateService rateService;


    @PostMapping("/getRate")
    public void getRate(@RequestBody Rate rate) {
        rateService.saveRate(rate);
    }

    @GetMapping("/getOnly")
    public List<Rate> getOnly(String curField) {
        return rateService.getOnly(curField);
    }

    @GetMapping("/getNation")
    public List<CurrencyJson> getNation() {
        return rateService.getNation();
    }


}
