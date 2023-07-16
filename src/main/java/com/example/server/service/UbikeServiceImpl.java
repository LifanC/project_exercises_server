package com.example.server.service;

import com.example.server.mapper.UbikeMapper;
import com.example.server.model.Ubike;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class UbikeServiceImpl implements UbikeService {

    @Resource
    private UbikeMapper ubikeMapper;

    public String dateFormat(String TorY) {
        Date date = new Date();
        SimpleDateFormat strDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
//        1.
//        switch (TorY){
//            case "Today":
//                return strDate.format(date);
//            case "Yesterday":
//                return strDate.format(calendar.getTime());
//        }
        return switch (TorY) {
            case "Today" -> strDate.format(date);
            case "Yesterday" -> strDate.format(calendar.getTime());
            default -> null;
        };
    }

    @Override
    public void queryUbike() {
        ubikeMapper.truncateTable();
    }

    @Override
    public void saveUbike(Ubike ubike) {
        ubikeMapper.insertUbike(ubike);
    }

    @Override
    public List<Ubike> getOnlyList(String sarea, String ar) {
        List<Ubike> dataA = ubikeMapper.selectUbike(sarea, ar, dateFormat("Today"));
        return (dataA.size() == 0) ? ubikeMapper.selectUbike(sarea, ar, dateFormat("Yesterday")) : ubikeMapper.selectUbike(sarea, ar, dateFormat("Today"));
    }

    @Override
    public List<Ubike> getOnlyLists(String sarea) {
        List<Ubike> dataA = ubikeMapper.selectUbikes(sarea, dateFormat("Today"));
        return (dataA.size() == 0) ? ubikeMapper.selectUbikes(sarea, dateFormat("Yesterday")) : ubikeMapper.selectUbikes(sarea, dateFormat("Today"));
    }
}
