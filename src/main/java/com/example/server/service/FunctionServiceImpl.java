package com.example.server.service;

import com.example.server.mapper.FunctionMapper;
import com.example.server.model.CustomerData;
import com.example.server.model.CustomerDataMoney;
import com.example.server.model.SymmetricDecryption;
import com.example.server.model.SymmetricEncryption;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FunctionServiceImpl implements FunctionService {

    @Resource
    private FunctionMapper functionMapper;

    private final String btye_key = "0123456789abcdef";

    public String timeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public String formatteryyyyMMdd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    /**
     * 註冊
     */
    @Override
    public void register(CustomerData customerData) throws Exception {
        int count = functionMapper.countUserName(customerData);
        int randomNumber = (int) (Math.random() * 10000) + 1;
        String randomString = String.format("%04d", randomNumber);
        if (count == 0) {
            //密碼加密
            String password = SymmetricEncryption.encryption(customerData.getPassWord(),btye_key);
            customerData.setPassWord(password);
            customerData.setCreateTime(timeFormatter());
            customerData.setUserNameId(formatteryyyyMMdd() + randomString);
            CustomerDataMoney customerDataMoney = new CustomerDataMoney();
            customerDataMoney.setUserNameId(formatteryyyyMMdd() + randomString);
            customerDataMoney.setUserName(customerData.getUserName());
            customerDataMoney.setCreateTime(timeFormatter());
            functionMapper.register(customerData);
            functionMapper.registerMoney(customerDataMoney);
        }
    }

    /**
     * 登入
     */
    @Override
    public List<CustomerData> login(CustomerData customerData) throws Exception {
        List<CustomerData> list = functionMapper.userName(customerData);
        List<String> data = new ArrayList<>();
        list.forEach(e->{
            data.add(e.getPassWord());
        });
        //密碼解密
        String password = SymmetricDecryption.decryption(data.get(0),btye_key);
        if(password.equals(customerData.getPassWord())){
            customerData.setPassWord(data.get(0));
            return functionMapper.login(customerData);
        }
        return null;
    }

    /**
     * 修改
     */
    @Override
    public List<CustomerDataMoney> edit(String userNameId, String passWord) throws Exception {
        //密碼加密
        String password = SymmetricEncryption.encryption(passWord,btye_key);
        functionMapper.edit(userNameId, password, timeFormatter());
        return functionMapper.userNameId(userNameId);
    }

    /**
     * 查詢function_money的資料
     */
    @Override
    public List<CustomerDataMoney> function_money_userNameId(String userNameId) {
        return functionMapper.userNameId(userNameId);
    }


    @Override
    public void save_cur(String userNameId, String cField, String cFieldMoney) {
        functionMapper.save_cur(userNameId, cField, cFieldMoney);
    }

    @Override
    public List<CustomerDataMoney> putAddMoney(String setMoney, String curFieldMoney, String userNameId, String depositOrWithdrawMoney) {
        List<CustomerDataMoney> data = functionMapper.userNameId(userNameId);
        Map map = new HashMap<>();
        if ("depositMoney".equals(depositOrWithdrawMoney)) {
            data.forEach(z -> {
                BigDecimal depositNew = new BigDecimal(setMoney);
                BigDecimal depositOld = new BigDecimal(String.valueOf(z.getEx_money()));
                //錢(新)+錢(舊)
                BigDecimal depositNew_Add_depositOld = depositNew.add(depositOld);
                BigDecimal a = new BigDecimal(curFieldMoney);
                BigDecimal b = depositNew.multiply(a);
                BigDecimal c = new BigDecimal(String.valueOf(z.getShow_money()));
                //錢(*匯率)+錢(原本匯率)
                BigDecimal dAdde = b.add(c).setScale(0, RoundingMode.HALF_UP);
                map.put("ex_money", depositNew_Add_depositOld);
                map.put("show_money", dAdde);
                map.put("user_name_id", userNameId);
                map.put("update_time", timeFormatter());
            });
        } else if ("withdrawMoney".equals(depositOrWithdrawMoney)) {
            data.forEach(y -> {
                BigDecimal withdrawNew = new BigDecimal(setMoney);
                BigDecimal withdrawOld = new BigDecimal(String.valueOf(y.getEx_money()));
                //錢(舊)-錢(新)
                BigDecimal withdrawOld_Subtract_withdrawNew = withdrawOld.subtract(withdrawNew);
                BigDecimal a = new BigDecimal(curFieldMoney);
                BigDecimal b = withdrawNew.multiply(a);
                BigDecimal c = new BigDecimal(String.valueOf(y.getShow_money()));
                //錢(原本匯率)-錢(*匯率)
                BigDecimal dSubtract = c.subtract(b).setScale(0, RoundingMode.HALF_UP);
                map.put("ex_money", withdrawOld_Subtract_withdrawNew);
                map.put("show_money", dSubtract);
                map.put("user_name_id", userNameId);
                map.put("update_time", timeFormatter());
            });
        }
        functionMapper.setNameId(map);
        return functionMapper.userNameId(userNameId);
    }
}
