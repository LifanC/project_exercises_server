package com.example.server.controller;

import com.example.server.model.CustomerData;
import com.example.server.model.CustomerDataMoney;
import com.example.server.model.Ins_del;
import com.example.server.service.FunctionService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Component
@RestController
@CrossOrigin(origins = "*")
@Transactional
@RequestMapping("/function")
public class FunctionController {

    @Resource
    RedisTemplate<String, String> redisTemplate;
    @Resource
    private FunctionService functionService;

    public void setSerializer() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
    }


    /**
     * 註冊
     */
    @PostMapping("/register")
    public void register(@RequestBody CustomerData customerData) throws Exception {
        functionService.register(customerData);
    }

    /**
     * 登入
     */
    @PostMapping("/login")
    public List<String> login(@RequestBody CustomerData customerData) {
        setSerializer();
        try {
            List<CustomerData> login = functionService.login(customerData);
            String userKey = "userName";
            String userValue = login.get(0).getUserName();
            String useridKey = "userNameId";
            String useridValue = login.get(0).getUserNameId();
            redisTemplate.opsForValue().setIfAbsent(userKey, userValue);
            redisTemplate.opsForValue().setIfAbsent(useridKey, useridValue);
            List<String> data = new ArrayList<>();
            data.add(redisTemplate.opsForValue().get("userName"));
            data.add(redisTemplate.opsForValue().get("userNameId"));
            return data;
        } catch (Exception e) {
            List<String> dataError = new ArrayList<>();
            dataError.add("登入失敗，請再輸入一次");
            dataError.add("");
            return dataError;
        }
    }

    /**
     * 登出
     */
    @GetMapping("/sign_out")
    public List<String> sign_out() throws Exception {
        redisTemplate.delete("userName");
        redisTemplate.delete("userNameId");
        List<String> list = new ArrayList<>();
        list.add("未登入");
        list.add("");
        return list;
    }

    /**
     * 修改
     */
    @PutMapping("/edit/{userNameId}/{passWord}")
    public List<CustomerDataMoney> edit(
            @PathVariable String userNameId,
            @PathVariable String passWord
    ) throws Exception {
        return functionService.edit(userNameId, passWord);
    }

    /**
     * 查詢function_money的資料
     */
    @GetMapping("/function_money")
    public List<CustomerDataMoney> function_money(@Param("userNameId") String userNameId) {
        return functionService.function_money_userNameId(userNameId);
    }

    /**
     * 存幣別、匯率
     */
    @PutMapping("/save_cur/{userNameId}/{cField}/{cFieldMoney}")
    public List<CustomerDataMoney> save_cur(
            @PathVariable String userNameId,
            @PathVariable String cField,
            @PathVariable String cFieldMoney
    ) {
        functionService.save_cur(userNameId, cField, cFieldMoney);
        return functionService.function_money_userNameId(userNameId);
    }

    @PutMapping("/putAddMoney/{setMoney}/{curFieldMoney}/{userNameId}/{depositOrWithdrawMoney}")
    public List<CustomerDataMoney> putAddMoney(
            @PathVariable String setMoney,
            @PathVariable String curFieldMoney,
            @PathVariable String userNameId,
            @PathVariable String depositOrWithdrawMoney
    ) {
        return functionService.putAddMoney(setMoney, curFieldMoney, userNameId, depositOrWithdrawMoney);
    }

    /**
     * 查詢密碼
     */
    @GetMapping("/findPassword")
    public String findPassword(@Param("userName") String userName) throws Exception {
        return functionService.findPassword(userName);
    }


    //Index
    @PostMapping("/ins_del")
    public List<Ins_del> ins_del(@RequestBody Ins_del ins_del) {
        return functionService.ins_del(ins_del);
    }

    /**
     * 顯示資料
     */
    @GetMapping("/get_ins_del")
    public List<Ins_del> get_ins_del(Ins_del ins_del) {
        return functionService.get_ins_del(ins_del);
    }

    @GetMapping("/findDatePicker")
    public List<Ins_del> findDatePicker(
            @Param("DatePickerStart") String DatePickerStart,
            @Param("DatePickerEnd") String DatePickerEnd
    ) {
        return functionService.findDatePicker(DatePickerStart, DatePickerEnd);
    }

    @GetMapping("/findIns_del")
    public List<Ins_del> findIns_del(
            @Param("DatePickerStart") String DatePickerStart,
            @Param("DatePickerEnd") String DatePickerEnd
    ) {
        return functionService.findIns_del(DatePickerStart, DatePickerEnd);
    }

    @PostMapping("/setTableData")
    public List<Ins_del> setTableData(@RequestBody Ins_del ins_del) {
        return functionService.setTableData(
                ins_del.getIns_del_data_id(),
                ins_del.getExpense_and_income_number(),
                ins_del.getInputMoney(),
                ins_del.getSetInputMoney(),
                ins_del.getCalendarDetails()
        );
    }

}
