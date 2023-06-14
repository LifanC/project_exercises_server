package com.example.server.service;

import com.example.server.mapper.FunctionMapper;
import com.example.server.model.*;
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
        int randomNumber = (int) (Math.random() * 100000000) + 1;
        String randomString = String.format("%08d", randomNumber);
        if (count == 0) {
            //密碼加密
            String password = SymmetricEncryption.encryption(customerData.getPassWord(), btye_key);
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
        list.forEach(e -> {
            data.add(e.getPassWord());
        });
        //密碼解密
        String password = SymmetricDecryption.decryption(data.get(0), btye_key);
        if (password.equals(customerData.getPassWord())) {
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
        String password = SymmetricEncryption.encryption(passWord, btye_key);
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

    @Override
    public List<Ins_del> ins_del(Ins_del ins_del) {
        if (!"".equals(ins_del.getDetails())) {
            functionMapper.addIns_DelData(ins_del);
        }
        int count = functionMapper.findAdd(ins_del);
        Map map = new HashMap<>();
        if (count == 0) {
            if ("1".equals(ins_del.getIns())) {
                switch (ins_del.getExpense_and_income_number()) {
                    case "A":
                        ins_del.setExpense(ins_del.getInputMoney());
                        ins_del.setIncome(0);
                        break;
                    case "B":
                        ins_del.setIncome(ins_del.getInputMoney());
                        ins_del.setExpense(0);
                        break;
                }
                functionMapper.add(ins_del);
                var list = functionMapper.findTotleMoney(ins_del);
                list.forEach(a -> {
                    ins_del.setTotleMoney(a.getTotleMoney());
                    map.put("calendarDetails", ins_del.getCalendarDetails());
                    map.put("totleMoney", a.getTotleMoney());
                });
                functionMapper.setTotle(map);
            }
        } else {
            if ("1".equals(ins_del.getIns())) {
                var setSecond = functionMapper.sel(ins_del);
                Integer expenseNumber = 0;
                Integer incomeNumber = 0;
                switch (ins_del.getExpense_and_income_number()) {
                    case "A":
                        for (Ins_del V : setSecond) {
                            expenseNumber = V.getExpense() + ins_del.getInputMoney();
                        }
                        break;
                    case "B":
                        for (Ins_del V : setSecond) {
                            incomeNumber = V.getIncome() + ins_del.getInputMoney();
                        }
                        break;
                }
                Map mapMoney = new HashMap<>();
                mapMoney.put("calendarDetails", ins_del.getCalendarDetails());
                mapMoney.put("expense", expenseNumber);
                mapMoney.put("income", incomeNumber);
                mapMoney.put("ex", ins_del.getEx());
                functionMapper.setSecond(mapMoney);
                var list = functionMapper.findTotleMoney(ins_del);
                list.forEach(a -> {
                    ins_del.setTotleMoney(a.getTotleMoney());
                    map.put("calendarDetails", ins_del.getCalendarDetails());
                    map.put("totleMoney", a.getTotleMoney());
                });
                functionMapper.setTotle(map);
            } else if ("1".equals(ins_del.getDel())) {
                functionMapper.set(ins_del);
            }
        }
        return functionMapper.sel(ins_del);
    }

    @Override
    public List<Ins_del> get_ins_del(Ins_del ins_del) {
        return functionMapper.sel(ins_del);
    }

    /**
     * 查詢密碼
     */
    @Override
    public String findPassword(String userName) throws Exception {
        CustomerData customerData = new CustomerData();
        customerData.setUserName(userName);
        var list = functionMapper.userName(customerData);
        var data = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(e -> {
                data.add(e.getPassWord());
            });
            String password = SymmetricDecryption.decryption(data.get(0).toString(), btye_key);
            return password;
        }
        return null;
    }

    @Override
    public List<Ins_del> findDatePicker(String DatePickerStart, String DatePickerEnd) {
        return functionMapper.findDatePicker(DatePickerStart, DatePickerEnd);
    }

    @Override
    public List<Ins_del> findIns_del(String DatePickerStart, String DatePickerEnd) {
        return functionMapper.findIns_del(DatePickerStart, DatePickerEnd);
    }

    @Override
    public List<Ins_del> setTableData(Long ins_del_data_id, String expense_and_income_number, Integer inputMoney, Integer setInputMoney, String calendarDetails) {
        Map map = new HashMap();
        var list = functionMapper.setTableDataId(ins_del_data_id);
        list.forEach(a -> {
            map.put("inputMoney", setInputMoney);
            map.put("ins_del_data_id", ins_del_data_id);
        });
        functionMapper.setInputMoney(map);
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();
        var list2 = functionMapper.fins_ins_del_id(calendarDetails);
        Integer z = inputMoney - setInputMoney;
        list2.forEach(b -> {
            map3.put("ins_del_id", b.getIns_del_id());
            switch (expense_and_income_number) {
                case "A":
                    map2.put("expense", b.getExpense() - z);
                    map2.put("ins_del_id", b.getIns_del_id());
                    break;
                case "B":
                    map2.put("income", b.getExpense() - z);
                    map2.put("ins_del_id", b.getIns_del_id());
                    break;
            }
        });
        functionMapper.setIns_del(map2);
        List<Ins_del> list3 = functionMapper.findTotleMoneyId(map3);
        list3.forEach(c -> {
            map4.put("totleMoney",c.getTotleMoney());
            map4.put("ins_del_id",c.getIns_del_id());
        });

        functionMapper.setTotleMoneyId(map4);
        return functionMapper.fins_ins_del_id(calendarDetails);
    }

}
