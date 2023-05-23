package com.example.server.controller;

import com.example.server.model.*;
import com.example.server.service.RateService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@CrossOrigin(origins = "*")
public class RateController{

    @Resource
    private RateService rateService;

    @PostMapping("/getRate")
    @Transactional
    public void getRate(@RequestBody Rate rate) {
        rateService.saveRate(rate);
    }

    @GetMapping("/getOnly")
    public List<Rate> getOnly(String curField) {
        return rateService.getOnly(curField);
    }

    @GetMapping("/getNationOnly")
    public List<NationNameAll> getNationOnly(String curField) {
        return rateService.getNationOnly(curField);
    }

    @GetMapping("/getNation")
    public List<CurrencyJson> getNation() {
        return rateService.getNation();
    }

    @GetMapping("/getNationName")
    public List<CurrencyJson> getNationName(String curField) {
        return rateService.getNationName(curField);
    }

    @GetMapping("/getNationNameAll")
    public List<NationNameAll> getNationNameAll(){
        return rateService.getNationNameAll();
    }

    /**
     * 新增
     * */
    @PostMapping("/fromSubmit")
    public List<UserMoney> fromSubmit(@RequestBody UserMoney userMoney){
        return rateService.fromSubmit(userMoney);
    }

    @GetMapping("/getUserMoney")
    public List<UserMoney> getUserMoney(UserMoney userMoney){
        return rateService.getUserMoney(userMoney);
    }

    /**
     * 刪除
     * */
    @DeleteMapping("/delId/{userId}/{userName}")
    public List<UserMoney> delId(
            @PathVariable("userId") Long userId,
            @PathVariable("userName") String userName
    ){
        return rateService.delId(userId,userName);
    }

    /**
     * 修改
     * */
    @PutMapping("/putAddMoney/{setMoney}/{curFieldMoney}/{userId}/{userName}")
    public List<UserMoney> putAddMoney(
            @PathVariable String setMoney,
            @PathVariable String curFieldMoney,
            @PathVariable Long userId,
            @PathVariable String userName
    ){
        return rateService.putAddMoney(setMoney,curFieldMoney,userId,userName);
    }




}
