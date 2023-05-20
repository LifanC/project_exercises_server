package com.example.server.controller;

import com.example.server.model.*;
import com.example.server.service.RateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class RateController {

    @Resource
    private RateService rateService;

    @PostMapping("/getRate")
    public List<String> getRate(@RequestBody Rate rate) {
        return rateService.saveRate(rate);
    }

    @GetMapping("/getAll")
    public List<Rate> getAll(){
        return rateService.getAll();
    }

    @GetMapping("/getOnly")
    public List<Rate> getOnly(String curField){
        return rateService.getOnly(curField);
    }

    @GetMapping("/getNation")
    public List<CurrencyJson> getNation(){
        return rateService.getNation();
    }

    @GetMapping("/getNationName")
    public List<CurrencyJson> getNationName(String curField){
        return rateService.getNationName(curField);
    }


}
